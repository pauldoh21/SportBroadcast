const channel = "overlays";

export default defineWebSocketHandler({
    open(peer) {
        console.log(`event=open id=${peer.id}`);
        peer.subscribe(channel);
    },

    message(peer, msg) {
        console.log(`message=${msg.text()}`)
        peer.publish(channel, msg.text())
    },

    close(peer, { code, reason }) {
        console.log(`event=close id=${peer.id} code=${code} reason=${reason}`);
        peer.unsubscribe(channel);
    },

    error(peer, error) {
        console.error(`event=error id=${peer.id} error=${error}`);
    },
})