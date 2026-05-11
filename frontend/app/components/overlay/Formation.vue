<script setup lang="ts">
import gsap from "gsap";
import { useSocket } from "~/composables/useSocket";

const { register } = useSocket();
const store = reactive({
  visible: false,
});
const overlay = useTemplateRef("overlay");

register(Overlays.Formation, (data) => {
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
      <div class="shirt-template">
        <svg width="0" height="0" version="1.1" xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink" xml:space="preserve" xmlns:serif="http://www.serif.com/">
            <g id="shirt">
                <path
                    d="M1004.996,378.855l-675.342,-0l-71.272,148.602l-34.632,34.585c-79.254,-57.288 -148.066,-125.855 -206.083,-206.083l36.59,-30.977l385.01,-90.837c31.042,-31.042 28.188,-176.946 61.109,-175.811c28.022,-35.822 118.538,-45.373 172.042,-45.373c53.504,0 144.02,9.551 172.042,45.373c32.921,-1.135 -4.952,149.157 26.089,180.199l420.168,86.567l36.451,30.859c-58.017,80.228 -126.829,148.796 -206.083,206.083l-34.663,-34.615l-81.424,-148.571Z"
                    style="fill:var(--primary-colour)" />
                <path
                    d="M672.417,1101.708c-132.783,0 -275,-10.92 -357.333,-22.502l0,-608.373l-56.695,56.62c-79.254,-57.288 -146.115,-122.272 -204.132,-202.5l260.827,-220.786c31.042,-31.042 152.371,-46.968 185.292,-45.833l344.083,0c32.921,-1.135 154.25,14.791 185.292,45.833l260.966,220.824c-58.017,80.228 -125.004,145.251 -204.258,202.539l-56.708,-56.696l0,608.373c-82.333,11.582 -224.55,22.502 -357.333,22.502Z"
                    style="fill:var(--secondary-colour);" />
            </g>
        </svg>
    </div>

    <div class="teamsheet">

        <div class="content-wrapper">

            <div class="team-badge">
                <img src="../../assets/badgeDynamos.png" />
            </div>

            <div class="team-name">Dunterlie Dynamos</div>

            <div class="main-content">

                <div class="teamsheet-section">

                    <div class="pitch-section">
                        <svg width="100%" height="100%" viewBox="0 0 1489 1658" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                            xml:space="preserve" xmlns:serif="http://www.serif.com/"
                            style="fill-rule:evenodd;clip-rule:evenodd;stroke-linecap:round;stroke-miterlimit:1.5;">
                            <g transform="matrix(1,0,0,1,-990.865003,-299.548018)">
                                <g transform="matrix(1.28,0,0,1.08,93.373816,12.653656)">
                                    <g transform="matrix(0.78125,0,0,0.972054,0,-88.92083)">
                                        <rect x="914.624" y="381.077" width="1454.114" height="1546.603"
                                            style="fill:none;stroke:var(--pitch-colour);stroke-width:18.54px;" />
                                    </g>
                                    <g transform="matrix(0.78125,0,0,0.925926,0,0)">
                                        <path d="M914.624,1115.853L2368.737,1115.853"
                                            style="fill:none;stroke:var(--pitch-colour);stroke-width:19.01px;" />
                                    </g>
                                    <g transform="matrix(0.78125,0,0,0.925926,0,0)">
                                        <circle cx="1641.681" cy="1115.853" r="187.563"
                                            style="fill:none;stroke:var(--pitch-colour);stroke-width:19.01px;" />
                                    </g>
                                    <g transform="matrix(0.800091,0,0,0.925926,-22.71593,0)">
                                        <rect x="1312.835" y="1611.688" width="637.153" height="315.991"
                                            style="fill:none;stroke:var(--pitch-colour);stroke-width:18.78px;" />
                                    </g>
                                    <g transform="matrix(0.800091,0,0,0.925926,-22.71593,-1210.797175)">
                                        <rect x="1312.835" y="1611.688" width="637.153" height="315.991"
                                            style="fill:none;stroke:var(--pitch-colour);stroke-width:18.78px;" />
                                    </g>
                                </g>
                            </g>
                        </svg>
                        <div class="player-shirt-container">
                            <div class="player-shirt">
                                <svg viewBox="0 0 1345 1115" preserveAspectRatio="xMidYMid meet"
                                    style="width:100%;object-fit:contain;">
                                    <use href="#shirt" style="object-fit:contain;" />
                                    <text x="50%" y="50%" text-anchor="middle" dominant-baseline="middle"
                                        font-size="500" class="shirt-number">26</text>
                                </svg>
                                <div class="shirt-name-container">
                                    <div class="shirt-name">Blyth</div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="details-section">
                    <div class="details-section-background"></div>
                    <div class="manager">
                        <div class="manager-heading">MANAGER</div>
                        <div class="manager-picture">
                            <img src="../collins.png" alt="manager pic">
                        </div>
                        <div class="manager-name">Anthony Collins</div>
                    </div>

                    <div class="subs">
                        <div class="subs-heading">SUBS</div>
                        <div class="subs-list">
                            <div class="sub">14: Matthew Blyth</div>
                            <div class="sub">15: John Doe</div>
                            <div class="sub">16: Jane Smith</div>
                            <div class="sub">16: Jane Smith</div>
                            <div class="sub">16: Jane Smith</div>
                            <div class="sub">16: Jane Smith</div>
                            <div class="sub">16: Jane Smith</div>
                            <div class="sub">16: Jane Smith</div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
    </section>
  </template>
</template>

<style scoped>
.overlay {
  /* CSS Here */
}

.shirt-template {
            --primary-colour: #73AED8;
            --secondary-colour: #f5f5f5;
        }

        .teamsheet {
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: var(--font-display);
            color: var(--text-color);
            overflow: visible;
            min-height: 100vh;
            position: relative;

            --font-display: 'DM Serif Text', serif;
            --font-secondary: 'Haettenschweiler', sans-serif;
            --text-color: #dcebf4;
            --muted-color: rgba(0, 0, 0, 0.35);
            --muted-text: rgba(0, 0, 0, 0.45);
            --primary-colour: #73AED8;
            --secondary-colour: #f5f5f5;
            --ribbon-colour: #741920;
        }

        .content-wrapper {
            position: relative;
            display: flex;
            flex-direction: row;
            width: 65%;
            font-size: 1.2vw;
        }

        .main-content {
            box-shadow: 0 4px 24px rgba(0, 0, 0, 0.429);
            position: relative;
            display: flex;
            flex: 1;
            width: 100%;
        }

        .team-badge {
            position: absolute;
            top: -5%;
            width: 20%;
            height: auto;
            z-index: 10;
        }

        .team-badge img {
            width: 100%;
            height: 100%;
            object-fit: contain;
        }

        .team-name {
            writing-mode: vertical-rl;
            transform: rotate(180deg);
            color: #fff;
            padding: 16px 8px;
            font-style: italic;
            font-size: 3.6em;
            line-height: 1.2;
            display: flex;
            align-items: center;
            letter-spacing: 0;
            text-shadow:  0 4px 24px rgba(0, 0, 0, 0.429);
        }

        .teamsheet-section {
            position: relative;
            background: #f6f6f6cb;
            width: 70%;
            padding: 2% 3% 2% 5%;
        }

        .pitch-section {
            --pitch-colour: #ffffff;
            position: relative;
        }

        .player-shirt-container {
            position: absolute;
            width: 15%;
            height: auto;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .player-shirt {
            align-content: center;
            top: 0;
            left: 0;
            height: 100%;
            width: 100%;
            filter: drop-shadow(0 0 8px rgba(0, 0, 0, 0.16));
        }

        .shirt-number {
            fill: var(--primary-colour);
            font-style: italic;
        }

        .shirt-name-container {
            width: 100%;
            display: flex;
            justify-content: center;
        }

        .shirt-name {
            color: #000000;
            font-style: italic;
            font-size: 1.2em;
            line-height: 0.6;
        }

        .details-section {
            position: relative;
            display: flex;
            flex-direction: column;
            gap: 50px;
            color: #ffffff;
            width: 30%;
        }

        .details-section-background {
            position: absolute;
            inset: 0;
            background: var(--primary-colour);
            z-index: -2;
        }

        .details-section::after {
            content: '';
            position: absolute;
            inset: 0;
            z-index: 1;
            pointer-events: none;
            background: linear-gradient(270deg, rgb(0, 0, 0), rgba(0, 0, 0, 0));
            mix-blend-mode: soft-light;
        }

        .manager {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .manager-heading {
            font-size: 1.5em;
            margin-top: 20px;
            margin-bottom: 10px;
        }

        .manager-picture {
            width: 70%;
            height: auto;
            overflow: hidden;
            margin-bottom: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.557);
        }

        .manager-picture img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .manager-name {
            font-size: 1.5em;
            font-style: italic;
        }

        .subs {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            padding: 0 50px;
        }

        .subs-heading {
            font-family: var(--font-secondary);
            font-size: 3em;
            margin-bottom: 10px;
        }

        .subs-list {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .sub {
            font-size: 1.1em;
            font-style: italic;
            line-height: 0.95;
        }
</style>
