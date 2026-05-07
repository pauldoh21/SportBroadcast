<script setup lang="ts">
import gsap from "gsap";
import { useSocket } from "~/composables/useSocket";

const { register, publishLocal } = useSocket();

const store = reactive({
  visible: false,
  goals: {
    dun: 0,
    mal: 0,
  },
});
const overlay = useTemplateRef("overlay");
const ribbonBox = useTemplateRef("ribbonBox");
const homePanel = useTemplateRef("homePanel");
const awayPanel = useTemplateRef("awayPanel");
const homeScore = useTemplateRef("homeScore");
const awayScore = useTemplateRef("awayScore");
const timerBox = useTemplateRef("timerBox");
const leagueBadge = useTemplateRef("leagueBadge");

let animationTimeline: gsap.core.Timeline | null = null;

register(Overlays.MatchScorecard, (data) => {
  if (data.action === "play") play(data);
  if (data.action === "stop") return stop();
});

function animateRibbon(timeline: gsap.core.Timeline) {
  if (ribbonBox.value) {
    timeline.fromTo(
      ribbonBox.value,
      { opacity: 0, x: -50 },
      {
        opacity: 1,
        x: 0,
        duration: 0.6,
        ease: "back.out(1.7)",
      },
      0,
    );
  }
}

function animateLeagueBadge(timeline: gsap.core.Timeline) {
  if (leagueBadge.value) {
    timeline.fromTo(
      leagueBadge.value,
      { opacity: 0 },
      {
        opacity: 1,
        duration: 0.5,
        ease: "power2.out",
      },
      0.3,
    );
  }
}

function animateHomePanel(timeline: gsap.core.Timeline) {
  if (homePanel.value) {
    timeline.fromTo(
      homePanel.value,
      { opacity: 0, x: -40 },
      {
        opacity: 1,
        x: 0,
        duration: 0.6,
        ease: "power2.out",
      },
      0.4,
    );
  }
  if (homeScore.value) {
    timeline.fromTo(
      homeScore.value,
      { opacity: 0, scale: 0 },
      {
        opacity: 1,
        scale: 1,
        duration: 0.5,
        ease: "back.out(1.5)",
      },
      0.75,
    );
  }
}

function animateAwayPanel(timeline: gsap.core.Timeline) {
  if (awayPanel.value) {
    timeline.fromTo(
      awayPanel.value,
      { opacity: 0, x: 40 },
      {
        opacity: 1,
        x: 0,
        duration: 0.6,
        ease: "power2.out",
      },
      0.55,
    );
  }
  if (awayScore.value) {
    timeline.fromTo(
      awayScore.value,
      { opacity: 0, scale: 0 },
      {
        opacity: 1,
        scale: 1,
        duration: 0.5,
        ease: "back.out(1.5)",
      },
      0.85,
    );
  }
}

function animateTimer(timeline: gsap.core.Timeline) {
  if (timerBox.value) {
    timeline.fromTo(
      timerBox.value,
      { opacity: 0, scale: 0.8, y: 20 },
      {
        opacity: 1,
        scale: 1,
        y: 0,
        duration: 0.6,
        ease: "power2.out",
      },
      0.5,
    );
  }
}

async function play(data: any) {
  await publishLocal({
    overlay: Overlays.Penalties,
    action: "stop",
  });
  store.goals.dun = data.goals?.dun ?? 0;
  store.goals.mal = data.goals?.mal ?? 0;
  store.visible = true;
  nextTick(() => {
    if (overlay.value) {
      animationTimeline = gsap.timeline();
      animateRibbon(animationTimeline);
      animateLeagueBadge(animationTimeline);
      animateHomePanel(animationTimeline);
      animateAwayPanel(animationTimeline);
      animateTimer(animationTimeline);
    }
  });
}

function stop() {
  return new Promise<void>((resolve) => {
    nextTick(() => {
      if (!animationTimeline || animationTimeline.progress() === 0) {
        store.visible = false;
        resolve();
        return;
      }

      animationTimeline.reverse();

      gsap.delayedCall(animationTimeline.duration(), () => {
        store.visible = false;
        resolve();
      });
    });
  });
}
</script>

<template>
  <template v-if="store.visible === true">
    <section class="overlay" ref="overlay">
      <div class="broadcast">
        <div class="ribbon-box" ref="ribbonBox">
          <div class="ribbon"></div>
          <div class="ribbon"></div>
        </div>

        <!-- ── Main scorecard ── -->
        <div class="scorecard">
          <!-- Home: Dunterlie Dynamos -->
          <div class="panel team-home" ref="homePanel">
            <div class="team-home-background"></div>
            <div class="kit-swatches">
              <div class="kit-swatch dun"></div>
              <div class="kit-swatch"></div>
            </div>
            <span class="team-abbr">DUN</span>
            <div class="score-row">
              <div class="score home-score" ref="homeScore">
                {{ store.goals.dun }}
              </div>
            </div>
          </div>

          <div class="league-badge" ref="leagueBadge">
            <img src="../../assets/jmBall.png" alt="League Badge" />
          </div>

          <!-- Away: AC Malones -->
          <div class="panel team-away" ref="awayPanel">
            <div class="team-away-background"></div>
            <div class="score-row">
              <div class="score away-score" ref="awayScore">
                {{ store.goals.mal }}
              </div>
            </div>
            <span class="team-abbr">MAL</span>
            <div class="kit-swatches">
              <div class="kit-swatch mal"></div>
            </div>
          </div>
        </div>

        <!-- ── Timer box ── -->
        <div class="timer-box" ref="timerBox">
          <span class="timer-text">55:00</span>
        </div>
      </div>
    </section>
  </template>
</template>

<style scoped>
*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.overlay {
  min-height: 100vh;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  font-family: var(--font-display);
  color: var(--text-color);
  padding: 16px;
  overflow: visible;
}

/* ── Outer wrapper ── */
.broadcast {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  transform-origin: top left;
  transform: translateX(20px) scale(var(--ui-scale, 1));
  position: relative;
  overflow: visible;
  padding-inline: 40px;
}

/* ── Main scorecard panel ── */
.scorecard {
  display: flex;
  align-items: stretch;
  height: 90px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.35);
  position: relative;
  z-index: 1;
}

/* ── Shared panel base ── */
.panel {
  background: #ecdfc5;
  position: relative;
  display: flex;
  overflow: hidden;
  gap: 10px;
}

/* Watermark crest behind each team panel */
.panel::before {
  content: "";
  position: absolute;
  inset: 0;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  opacity: 0.1;
  pointer-events: none;
  color: var(--muted-color);
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
  background: linear-gradient(90deg, rgb(0, 0, 0), rgba(0, 0, 0, 0) 55%);
  mix-blend-mode: overlay;
}

.team-home > :not(.team-home-background),
.team-away > :not(.team-away-background) {
  position: relative;
  z-index: 2;
}

/* ── Colour swatch (kit colour square) ── */
.kit-swatch {
  width: 100%;
  height: 100%;
  flex-shrink: 0;
}

.kit-swatch.dun {
  background: var(--home-colour-2);
}
.kit-swatch.mal {
  background: var(--away-colour-2);
}

.kit-swatches {
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  height: 100%;
  width: 30px;
}

/* ── Team abbreviation ── */
.team-abbr {
  line-height: 1;
  letter-spacing: 0.02em;
  z-index: 1;
  font-family: var(--font-secondary);
  font-size: 85px;
}

/* Central league badge — sits above the panel, overlapping */
.league-badge {
  transform-origin: center;
  position: absolute;
  left: 50%;
  transform: translateX(-50%) scale(1.15);
  height: 100%;
  z-index: 10;
}

.league-badge img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

/* Score row */
.score-row {
  position: relative;
  display: flex;
  align-items: center;
}

.score {
  font-size: 82px;
  font-style: italic;
  color: var(--text-color);
  min-width: 32px;
  text-align: center;
}

.home-score {
  margin-left: 100px;
  margin-right: 70px;
}

.away-score {
  margin-left: 70px;
  margin-right: 100px;
}

/* ── Timer box (separate brown block) ── */
.timer-box {
  background: #ffffff;
  height: 90px;
  min-width: 250px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.35);
  position: relative;
  z-index: 1;
}

.timer-text {
  font-size: 72px;
  color: #000000;
  letter-spacing: 0.04em;
  line-height: 1;
}

.ribbon-box {
  position: absolute;
  left: 5px;
  right: 5px;
  transform: skewX(-30deg);
  transform-origin: center;
  overflow: visible;
  height: 100%;
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
</style>
