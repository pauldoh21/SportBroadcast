<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    title: string;
    src: string;
    baseWidth?: number;
    baseHeight?: number;
  }>(),
  {
    baseWidth: 1920,
    baseHeight: 1080,
  },
);

const viewport = ref<HTMLElement | null>(null);
const scale = ref(1);

function updateScale() {
  if (!viewport.value) {
    return;
  }

  const widthRatio = viewport.value.clientWidth / props.baseWidth;
  const heightRatio = viewport.value.clientHeight / props.baseHeight;
  scale.value = Math.max(0.01, Math.min(widthRatio, heightRatio));
}

onMounted(() => {
  nextTick(() => {
    updateScale();
  });
});
</script>

<template>
  <div class="preview-card">
    <h2>{{ title }}</h2>
    <div ref="viewport" class="frame-viewport">
      <div
        class="frame-stage"
        :style="{
          width: `${baseWidth}px`,
          height: `${baseHeight}px`,
          '--frame-scale': scale,
        }"
      >
        <iframe class="graphics-frame" :src="src"></iframe>
      </div>
    </div>
  </div>
</template>

<style scoped>
.preview-card {
  width: 500px;
}

.frame-viewport {
  position: relative;
  width: 100%;
  aspect-ratio: 16 / 9;
  overflow: hidden;
  border-radius: 4px;
  border: 2px solid #11111b;
}

.frame-stage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(var(--frame-scale));
  transform-origin: center center;
}

.graphics-frame {
  width: 100%;
  height: 100%;
  border: 0;
}
</style>
