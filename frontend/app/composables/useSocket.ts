export const Overlays = {
    MatchScorecard: 'matchScorecard',
    Penalties: "penalties",
    Goal: "goal"
} as const
export type Overlay = (typeof Overlays)[keyof typeof Overlays]

const handlers: Partial<Record<Overlay, (payload: any) => void>> = {}

const { status, data } = useWebSocket('ws://localhost:3000/_ws')

watch(data, () => {
    const { overlay, ...rest } = JSON.parse(data.value)

    if (!isOverlay(overlay)) {
        console.error(`err=unsupported_overlay overlay=${overlay}`)
        return;
    }

    if (handlers[overlay] && status.value === "OPEN") {
        handlers[overlay](rest)
    }
})

export const useSocket = () => {
    const register = (overlay: Overlay, func: (payload: any) => void) => {
        handlers[overlay] = func
    }
    return { register }
}

const isOverlay = (overlay: string): overlay is Overlay => {
    return Object.values(Overlays).includes(overlay as Overlay)
}