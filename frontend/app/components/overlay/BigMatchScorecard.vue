<script setup lang="ts">
import gsap from "gsap";
import { useSocket } from "~/composables/useSocket";

const { register } = useSocket();
const store = reactive({
  visible: false,
});
const overlay = useTemplateRef("overlay");

register(Overlays.BigMatchScorecard, (data) => {
  if (data.action === "play") play(data);
  if (data.action === "stop") stop();
});

function play(data: any) {
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
  nextTick(() => {
    if (overlay.value) {
      gsap.fromTo(
        overlay.value,
        { opacity: 1, y: 0 },
        {
          opacity: 0,
          y: -10,
          duration: 0.5,
          ease: "power2.out",
          onComplete: () => (store.visible = false),
        },
      );
    }
  });
}
</script>

<template>
  <template v-if="store.visible === true">
    <section class="overlay" ref="overlay">
      <div class="broadcast-large">
        <div class="ribbon-box">
          <div class="ribbon"></div>
          <div class="ribbon"></div>
        </div>

        <div class="scorecard-large">

          <!-- Home: Dunterlie Dynamos -->
          <div class="panel large-panel team-home">
            <div class="team-home-background background-large"></div>
            <span class="team-abbr-large">DUN</span>

            <div class="team-details-row home-details">
              <div class="scorers">
                <div class="scorer">42: Drew Lennie (24')</div>
                <div class="scorer">17: Matthew Blyth (35')</div>
              </div>
              <div class="team-name team-name-home">Dunterlie Dynamos</div>
            </div>

            <div class="score-row">
              <div class="score-large home-score-large">2</div>
            </div>
          </div>

          <div class="league-badge-large">
            <img src="../../assets/jmBall.png" alt="League Badge" />
          </div>

          <!-- Away: AC Malones -->
          <div class="panel large-panel team-away">
            <div class="team-away-background background-large"></div>

            <div class="score-row">
              <div class="score-large away-score-large">1</div>
            </div>

            <div class="team-details-row away-details">
              <div class="scorers">
                <div class="scorer">35: Joe Donnelly (42')</div>
              </div>
              <div class="team-name team-name-away">AC Malones</div>
            </div>

            <span class="team-abbr-large">MAL</span>
          </div>

        </div>

        <div class="timer-box-large">
          <span class="timer-text-large timer">55:00</span>
        </div>

      </div>
    </section>
  </template>
</template>

<style scoped>
.overlay {
    --large-scoreboard-x-padding: 40px;

    min-height: 100vh;
    display: flex;
    align-items: flex-start;
    justify-content: flex-start;
    font-family: var(--font-display);
    color: var(--text-color);
    padding: 16px;
    overflow: hidden;

  }

.broadcast-large {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 15px;
      position: fixed;
      left: 0;
      right: 0;
      bottom: 100px;
      margin: 0 auto;
      width: max-content;
      overflow: visible;
      padding-inline: 90px;
      z-index: 5;
      transform-origin: bottom center;
      transform: scale(1.5);
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
      box-shadow: 0 4px 24px rgba(0,0,0,0.35);
    }

    .scorecard-large {
      display: flex;
      align-items: stretch;
      height: 160px;
      box-shadow: 0 4px 24px rgba(0,0,0,0.758);
      position: relative;
      z-index: 1;
      padding: 0 var(--large-scoreboard-x-padding);
    }

    .panel {
      background: #ECDFC5;
      position: relative;
      display: flex;
      overflow: hidden;
      gap: 10px;
    }

    .large-panel {
      width: 28vw;
      overflow: visible;
      align-items: flex-end;
      padding-bottom: 2px;
    }

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

    .background-large {
      margin: 0 calc(var(--large-scoreboard-x-padding) * -1);
    }

    .team-home::after {
      content: '';
      position: absolute;
      inset: 0;
      z-index: 1;
      pointer-events: none;
      background: linear-gradient(90deg, rgba(0,0,0,0) 55%, rgb(0,0,0));
      mix-blend-mode: soft-light;
    }

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
      content: '';
      position: absolute;
      inset: 0;
      z-index: 1;
      pointer-events: none;
      background: linear-gradient(90deg, rgb(0,0,0), rgba(0,0,0,0) 55%);
      mix-blend-mode: overlay;
    }

    .team-home > :not(.team-home-background),
    .team-away > :not(.team-away-background) {
      position: relative;
      z-index: 2;
    }

    .team-abbr-large {
      line-height: 1;
      letter-spacing: 0.02em;
      z-index: 1;
      font-family: var(--font-secondary);
      font-size: 170px;
    }

    .team-details-row {
      align-content: end;
      width: 50%;
    }

    .home-details {
      justify-content: flex-start;
      text-align: left;
    }

    .away-details {
      justify-content: flex-end;
      text-align: right;
    }

    .team-name {
      font-size: 50px;
      overflow: hidden;
      text-overflow: clip;
      white-space: nowrap;
      margin-top: -13px;
    }

    .team-name-home {
      mask-image: linear-gradient(to right, rgba(0,0,0,1) 90%, rgba(0,0,0,0));
    }

    .team-name-away {
      mask-image: linear-gradient(to left, rgba(0,0,0,1) 90%, rgba(0,0,0,0));
      direction: rtl;
    }

    .scorers {
      font-size: 20px;
      font-style: italic;
      width: 100%;
      max-height: 87px;
      overflow-y: scroll;
      text-overflow: clip;
      scrollbar-width: none;
      align-content: flex-end;
      mask-image: linear-gradient(to bottom, rgba(0,0,0,0) 0%, rgba(0,0,0,1) 15%, rgba(0,0,0,1) 85%, rgba(0,0,0,0));
    }

    .scorer {
      display: block;
    }

    .score-row {
      position: relative;
      display: flex;
      align-items: center;
    }

    .score-large {
      font-size: 76px;
      font-style: italic;
      color: var(--text-color);
      min-width: 32px;
      text-align: center;
      transform: scale(4.5);
      overflow: visible;
      padding-bottom: 14px;
      text-shadow: 0 0px 3px rgba(0,0,0,0.669);
    }

    .home-score-large {
      margin-left: 10px;
    }

    .away-score-large {
      margin-right: 10px;
    }

    .league-badge-large {
      transform-origin: center;
      position: absolute;
      left: 50%;
      transform: translateX(-50%) scale(1.35);
      height: 100%;
      z-index: 10;
    }

    .league-badge-large img {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }

    .timer-box-large {
      background: #ffffff;
      height: 60px;
      min-width: 190px;
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: 0 4px 24px rgba(0,0,0,0.35);
      transform-origin: center;
      position: absolute;
      left: 50%;
      transform: translateX(-50%) translateY(-160%);
      top: 0%;
      z-index: 1;
    }

    .timer-text-large {
      font-size: 52px;
      color: #000000;
      letter-spacing: 0.04em;
      line-height: 1;
    }
</style>
