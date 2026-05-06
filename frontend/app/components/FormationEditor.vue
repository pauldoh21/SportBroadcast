<script setup lang="ts">
type Player = {
  forename: string;
  surname: string;
  number: number;
};
type Team = {
  name: string;
  players: Player[];
};
type Zone = "lineup" | "substitutes";
type FormationRow = {
  startIndex: number;
  players: Player[];
};

const defaultFormation = "4-4-2";
const formations = [
  defaultFormation,
  "4-3-3",
  "3-5-2",
  "5-3-2",
  "3-4-3",
  "4-2-3-1",
  "3-2-3-1-1",
];

const { team } = defineProps<{
  team: Team;
}>();

const selectedFormation = ref<string>(defaultFormation);
const rowSizes = ref<number[]>(parseFormation(selectedFormation.value));
const lineupPlayers = ref<Player[]>([]);
const substitutes = ref<Player[]>([]);
const dragSource = ref<{ zone: Zone; index: number } | null>(null);
const selectedPlayerNumber = ref<number | null>(null);

const formationRows = computed<FormationRow[]>(() => {
  const rows: FormationRow[] = [];
  let startIndex = 0;

  for (const size of rowSizes.value) {
    rows.push({
      startIndex,
      players: lineupPlayers.value.slice(startIndex, startIndex + size),
    });
    startIndex += size;
  }

  return rows;
});

watch(
  () => team.players,
  (players) => {
    lineupPlayers.value = players.slice(0, 11);
    substitutes.value = players.slice(11);
  },
  { immediate: true },
);

function parseFormation(formation: string): number[] {
  const split = formation
    .split("-")
    .map((row) => Number.parseInt(row))
    .filter((row) => Number.isFinite(row));
  return [1, ...split];
}

function formationChange() {
  rowSizes.value = parseFormation(selectedFormation.value);
}

function onDragStart(zone: Zone, index: number, event: DragEvent) {
  dragSource.value = { zone, index };

  if (event.dataTransfer) {
    event.dataTransfer.effectAllowed = "move";
    event.dataTransfer.setData("text/plain", `${zone}:${index}`);
  }
}

function onDragEnd() {
  dragSource.value = null;
}

function onDrop(targetZone: Zone, targetIndex: number) {
  if (!dragSource.value) {
    return;
  }

  const source = dragSource.value;
  dragSource.value = null;

  if (source.zone === targetZone && source.index === targetIndex) {
    return;
  }

  const sourcePlayers =
    source.zone === "lineup" ? lineupPlayers.value : substitutes.value;
  const targetPlayers =
    targetZone === "lineup" ? lineupPlayers.value : substitutes.value;
  const sourcePlayer = sourcePlayers[source.index];
  const targetPlayer = targetPlayers[targetIndex];

  if (!sourcePlayer || !targetPlayer) {
    return;
  }

  sourcePlayers[source.index] = targetPlayer;
  targetPlayers[targetIndex] = sourcePlayer;
}

function isPlayerSelected(playerNumber: number): boolean {
  return selectedPlayerNumber.value === playerNumber;
}

function togglePlayer(player: Player) {
  selectedPlayerNumber.value =
    selectedPlayerNumber.value === player.number ? null : player.number;
}
</script>

<template>
  <section>
    <div>{{ team.name }}</div>
    <div>
      <label>Formation: </label>
      <select v-model="selectedFormation" @change="formationChange">
        <option
          v-for="(formation, index) in formations"
          :key="`${index}-${formation}`"
          :value="formation"
        >
          {{ formation }}
        </option>
      </select>
    </div>
    <div class="formation-view">
      <ol class="formation-lines">
        <li
          v-for="(row, rowIndex) in formationRows"
          :key="`row-${rowIndex}-${row.players.length}`"
          class="formation-line"
        >
          <button
            v-for="(player, playerIndex) in row.players"
            :key="`row-${rowIndex}-player-${player.number}-${playerIndex}`"
            type="button"
            :class="[
              'player-dot',
              { 'player-dot-selected': isPlayerSelected(player.number) },
            ]"
            :title="`${player.forename} ${player.surname}`"
            :aria-label="`${player.forename} ${player.surname}`"
            :aria-pressed="isPlayerSelected(player.number)"
            draggable="true"
            @click="togglePlayer(player)"
            @dragstart="
              onDragStart('lineup', row.startIndex + playerIndex, $event)
            "
            @dragover.prevent
            @drop="onDrop('lineup', row.startIndex + playerIndex)"
            @dragend="onDragEnd"
          >
            {{ player.number }}
          </button>
        </li>
      </ol>
    </div>
    <div class="substitutes-row" aria-label="Substitutes">
      <span
        v-for="(substitute, subIndex) in substitutes"
        :key="`substitute-${substitute.number}-${subIndex}`"
        :class="['player-dot', 'substitute-dot']"
        :title="`${substitute.forename} ${substitute.surname}`"
        :aria-label="`${substitute.forename} ${substitute.surname}`"
        draggable="true"
        @dragstart="onDragStart('substitutes', subIndex, $event)"
        @dragover.prevent
        @drop="onDrop('substitutes', subIndex)"
        @dragend="onDragEnd"
      >
        {{ substitute.number }}
      </span>
    </div>
  </section>
</template>

<style lang="css" scoped>
.formation-view {
  --pitch-light: #347f4d;
  --pitch-dark: #2b6e42;
  --line: rgb(255 255 255 / 0.42);
  --player-size: clamp(22px, 4vw, 38px);
  --line-gap: clamp(14px, 3vh, 30px);

  position: relative;
  width: 300px;
  aspect-ratio: 2 / 3;
  padding: clamp(8px, 2vw, 15px);
  border-radius: 18px;
  background: repeating-linear-gradient(
    180deg,
    var(--pitch-light) 0 12.5%,
    var(--pitch-dark) 12.5% 25%
  );
  border: 2px solid rgb(255 255 255 / 0.35);
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.5);
}

.formation-view::before {
  content: "";
  position: absolute;
  left: 50%;
  transform: translateX(-50%) translateY(-30%);
  width: min(75%, 220px);
  height: calc(var(--player-size) + 18px);
  border: 4px solid rgb(255 255 255 / 0.6);
}

.formation-lines {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  gap: var(--line-gap);
  height: 100%;
  margin: 0;
  padding: 0;
  list-style: none;
}

.formation-line {
  position: relative;
  z-index: 1;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  gap: clamp(8px, 2vw, 20px);
  min-height: var(--player-size);
}

.player-dot {
  display: grid;
  place-items: center;
  padding: 0;
  inline-size: var(--player-size);
  block-size: var(--player-size);
  cursor: grab;
  user-select: none;
  border-radius: 999px;
  background: linear-gradient(160deg, #f5f7fa 0%, #dce2eb 100%);
  border: 2px solid #11111b75;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.5);
  color: #1f2937;
  font-weight: 700;
}

.player-dot-selected {
  background: linear-gradient(160deg, #60a5fa 0%, #2563eb 100%);
  border-color: #1e3a8a;
  color: #eff6ff;
}

.player-dot:active {
  cursor: grabbing;
}

.substitutes-row {
  display: flex;
  gap: 10px;
  width: 325px;
  margin-top: 10px;
  padding: 6px 2px;
  overflow-x: auto;
  overflow-y: hidden;
  scrollbar-width: thin;
}

.substitute-dot {
  flex: 0 0 auto;
  inline-size: clamp(26px, 3.2vw, 34px);
  block-size: clamp(26px, 3.2vw, 34px);
}
</style>
