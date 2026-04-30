<script setup lang="ts">
import gsap from 'gsap'
import { useSocket } from '~/composables/useSocket'
const { register } = useSocket()

const store = reactive({
    visible: false,
    goals: {
        dun: 0,
        mal: 0
    }
})
const matchScorecardRef = useTemplateRef('matchScorecardRef')

register(Overlays.MatchScorecard, (data) => {
    if (data.action === "play") play(data)
    if (data.action === "stop") stop()
})

const play = (data: any) => {
    store.goals.dun = data.goals?.dun ?? 0
    store.goals.mal = data.goals?.mal ?? 0
    store.visible = true
    nextTick(() => {
        if (matchScorecardRef) {
            gsap.fromTo(
                matchScorecardRef.value,
                { opacity: 0, y: -10 },
                {
                    opacity: 1,
                    y: 0,
                    duration: 0.5,
                    ease: "power2.out",
                },
            )
        }
    })
}

const stop = () => {
    nextTick(() => {
        if (matchScorecardRef) {
            gsap.fromTo(
                matchScorecardRef.value,
                { opacity: 1, y: 0, },
                {
                    opacity: 0,
                    y: -10,
                    duration: 0.5,
                    ease: "power2.out",
                    onComplete: () => store.visible = false
                },
            )
        }
    })
}
</script>

<template>
    <div v-if="store.visible === true" class="data" ref="matchScorecardRef">
        <p>DUN {{ store.goals.dun }}-{{ store.goals.mal }} MAL</p>
    </div>
</template>

<style scoped>
.data {
    p {
        color: aliceblue;
        background-color: rebeccapurple;
    }
}
</style>
