<script setup lang="ts">
import data from "~/assets/data.json";
import FormationEditor from "~/components/formation/FormationEditor.client.vue";
</script>

<template>
  <main class="main">
    <section class="control">
      <div>
        <button>Start Match Timer</button>
        <button>Pause Match Timer</button>
      </div>
      <FormationEditor :team="data.teams.dunterlie_dynamos" />
      <FormationEditor :team="data.teams.ac_malones" />
    </section>
    <section class="preview">
      <ScaledFrame title="PREVIEW" src="/preview" />
      <ScaledFrame title="LIVE" src="/live" />
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
