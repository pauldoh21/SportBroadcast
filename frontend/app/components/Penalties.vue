<script setup lang="ts">
const { status, data, send, open, close, ws } = useWebSocket('ws://localhost:3000/_ws')

const state = reactive({
    data: "",
    visible: false
})

watch(data, () => {
    const parse = JSON.parse(data.value)
    state.data = parse.data
    state.visible = parse.visible
})

</script>

<template>
    <div v-if="state.visible === true" class="data">
        <p>Data Driven Component (Penalties): {{ state.data }}</p>
    </div>
</template>

<style>
.data {
    background-color: rebeccapurple;

    p {
        color: aliceblue;
    }
}
</style>
