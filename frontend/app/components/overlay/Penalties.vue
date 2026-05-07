<script setup lang="ts">
import gsap from "gsap";
import { useSocket } from "~/composables/useSocket";

const { register, publishLocal } = useSocket();
const store = reactive({
  visible: false,
});
const overlay = useTemplateRef("overlay");

register(Overlays.Penalties, (data) => {
  if (data.action === "play") play(data);
  if (data.action === "stop") return stop();
});

async function play(data: any) {
  await publishLocal({
    overlay: Overlays.MatchScorecard,
    action: "stop",
  });

  store.visible = true;
  nextTick(() => {
    if (overlay.value) {
      gsap.fromTo(
        overlay.value,
        { opacity: 0, y: -10 },
        {
          opacity: 1,
          y: 0,
          duration: 0.5,
          ease: "power2.out",
        },
      );
    }
  });
}

function stop() {
  return new Promise<void>((resolve) => {
    nextTick(() => {
      if (!overlay.value) {
        store.visible = false;
        resolve();
        return;
      }

      gsap.fromTo(
        overlay.value,
        { opacity: 1, y: 0 },
        {
          opacity: 0,
          y: -10,
          duration: 0.5,
          ease: "power2.out",
          onComplete: () => {
            store.visible = false;
            resolve();
          },
        },
      );
    });
  });
}
</script>

<template>
  <template v-if="store.visible === true">
    <div class="overlay" ref="overlay">
      <div class="broadcast">
        <div class="ribbon-box">
          <div class="ribbon"></div>
          <div class="ribbon"></div>
        </div>

        <div class="scorecard">
          <div class="panel team-home">
            <div class="team-home-background"></div>
            <div class="abbreviation-box">
              <div class="abbreviation">DUN</div>
            </div>
            <div class="pens-row">
              <div class="pen pen-scored"></div>
              <div class="pen pen-missed"></div>
              <div class="pen"></div>
              <div class="pen"></div>
              <div class="pen"></div>
            </div>
            <div class="score-box">
              <div class="score">1</div>
            </div>
          </div>
          <div class="panel team-away">
            <div class="team-away-background"></div>
            <div class="abbreviation-box">
              <div class="abbreviation">MAL</div>
            </div>
            <div class="pens-row">
              <div class="pen pen-scored"></div>
              <div class="pen pen-missed"></div>
              <div class="pen"></div>
              <div class="pen"></div>
              <div class="pen"></div>
            </div>
            <div class="score-box">
              <div class="score">1</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
</template>

<style scoped>
.overlay {
  min-height: 200vh;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  font-family: var(--font-display);
  color: var(--text-color);
  padding: 16px;
  overflow: visible;
  top: 10px;
}

.broadcast {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  transform-origin: top left;
  transform: translateX(20px) scale(1.25);
  position: relative;
  overflow: visible;
  padding-inline: 40px;
}

/* ── Ribbon box (decorative skewed stripes behind everything) ── */
.ribbon-box {
  position: absolute;
  left: 5px;
  right: 5px;
  transform: skewX(-30deg);
  transform-origin: center;
  overflow: visible;
  height: 50%;
  z-index: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10%;
}

.ribbon {
  background: var(--ribbon-colour);
  width: 100%;
  height: 30%;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.35);
}

.scorecard {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  height: 180px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.35);
  position: relative;
  z-index: 1;
}

.panel {
  background: #ecdfc5;
  position: relative;
  display: flex;
  overflow: hidden;
  gap: 20px;
}

/* ── Home team panel ── */
.team-home {
  position: relative;
  color: var(--home-colour-2);
}

.team-home-background {
  position: absolute;
  inset: 0;
  background: var(--home-colour-1);
  z-index: 0;
}

.team-home::after {
  content: "";
  position: absolute;
  inset: 0;
  z-index: 1;
  pointer-events: none;
  background: linear-gradient(90deg, rgba(0, 0, 0, 0) 55%, rgb(0, 0, 0));
  mix-blend-mode: soft-light;
}

/* ── Away team panel ── */
.team-away {
  position: relative;
  justify-content: flex-end;
  color: var(--away-colour-2);
}

.team-away-background {
  position: absolute;
  inset: 0;
  background: var(--away-colour-1);
  z-index: 0;
}

.team-away::after {
  content: "";
  position: absolute;
  inset: 0;
  z-index: 1;
  pointer-events: none;
  background: linear-gradient(270deg, rgb(0, 0, 0), rgba(0, 0, 0, 0) 55%);
  mix-blend-mode: overlay;
}

/* Ensure content sits above the absolute background divs */
.team-home > :not(.team-home-background),
.team-away > :not(.team-away-background) {
  position: relative;
  z-index: 2;
}

.abbreviation-box {
  line-height: 1;
  letter-spacing: 0.02em;
  z-index: 1;
  font-family: var(--font-secondary);
  font-size: 85px;
  display: flex;
  align-content: center;
  justify-content: center;
  width: 150px;
}

.pens-row {
  display: flex;
  gap: 15px;
  align-items: center;
}

.pen {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  border: 4px solid var(--text-color);
  background: var(--muted-color);
}

.pen-scored {
  background: var(--pen-scored);
}

.pen-missed {
  background: var(--pen-missed);
}

.score-box {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  background: #ffffff;
}

.score {
  font-size: 82px;
  font-style: italic;
  color: #000000;
  text-align: center;
}
</style>
