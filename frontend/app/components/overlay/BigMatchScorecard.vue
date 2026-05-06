<script setup lang="ts">
import gsap from "gsap";
import { useSocket } from "~/composables/useSocket";

const { register } = useSocket();
const store = reactive({
  visible: false,
});
const ref = useTemplateRef("ref");

register(Overlays.BigMatchScorecard, (data) => {
  if (data.action === "play") play(data);
  if (data.action === "stop") stop();
});

function play(data: any) {
  store.visible = true;
  nextTick(() => {
    if (ref) {
      gsap.fromTo(
        ref.value,
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
    if (ref) {
      gsap.fromTo(
        ref.value,
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
    <section class="bigMatchScorecard" ref="ref">
      <h1>Data Driven Component (Big Match Scorecard)</h1>
      <!-- HTML Here -->
    </section>
  </template>
</template>

<style scoped>
.bigMatchScorecard {
  /* CSS Here */
}
</style>
