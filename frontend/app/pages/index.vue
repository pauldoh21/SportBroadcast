<script setup lang="ts">
import data from "~/assets/data.json";

const { publish } = useSocket();

function onPlay(overlay: Overlay) {
  publish({
    overlay,
    action: "play",
  });
}

function onStop(overlay: Overlay) {
  publish({
    overlay,
    action: "stop",
  });
}
</script>

<template>
  <main class="main">
    <section class="control">
      <div>
        <button @click="onPlay(Overlays.MatchScorecard)">
          Play MatchScorecard
        </button>
        <button @click="onStop(Overlays.MatchScorecard)">
          Stop MatchScorecard
        </button>
        <br />
        <button @click="onPlay(Overlays.BigMatchScorecard)">
          Play Big MatchScorecard
        </button>
        <button @click="onStop(Overlays.BigMatchScorecard)">
          Stop Big MatchScorecard
        </button>
        <br />
        <button @click="onPlay(Overlays.Penalties)">Play Penalties</button>
        <button @click="onStop(Overlays.Penalties)">Stop Penalties</button>
      </div>
      <FormationEditor :team="data.teams.dunterlie_dynamos" />
      <FormationEditor :team="data.teams.ac_malones" />
    </section>
    <section class="preview">
      <ScaledFrame title="PREVIEW" src="/preview" />
    </section>
  </main>
</template>

<style lang="css" scoped>
.main {
  display: flex;
  flex-direction: column;
}

.control {
  display: flex;
  justify-content: center;
  gap: 50px;
}

.preview {
  display: flex;
  justify-content: center;
  gap: 50px;
}
</style>
