<script setup lang="ts">
const store = reactive({
    data: "",
    visible: false
})
</script>

<template>
    <div v-if="store.visible === true" class="data">
        <p>Data Driven Component (Penalties): {{ store.data }}</p>
    </div>
</template>

<style scoped>
.data {
    background-color: rebeccapurple;

    p {
        color: aliceblue;
    }
}
</style>
