export const Overlays = {
  MatchScorecard: "matchScorecard",
  BigMatchScorecard: "bigMatchScorecard",
  Formation: "formation",
  Penalties: "penalties",
  Goal: "goal",
} as const;
export type Overlay = (typeof Overlays)[keyof typeof Overlays];

type OverlayHandler = (payload: any) => void | Promise<void>;
const handlers: Partial<Record<Overlay, OverlayHandler>> = {};

const { status, data, send } = useWebSocket("ws://localhost:3000/_ws");

watch(data, async () => {
  if (status.value !== "OPEN") {
    return;
  }
  await dispatchMessage(data.value);
});

export const useSocket = () => {
  const register = (overlay: Overlay, func: OverlayHandler) => {
    handlers[overlay] = func;
  };

  const publishLocal = async (payload: {
    overlay: Overlay;
    [key: string]: unknown;
  }) => {
    await dispatchByOverlay(payload.overlay, payload);
  };

  const publish = (data: unknown) => {
    send(JSON.stringify(data));
  };
  return { register, publish, publishLocal };
};

const dispatchMessage = async (rawData: string) => {
  const { overlay, ...rest } = JSON.parse(rawData);

  if (!isOverlay(overlay)) {
    console.error(`err=unsupported_overlay overlay=${overlay}`);
    return;
  }

  await dispatchByOverlay(overlay, rest);
};

const dispatchByOverlay = async (overlay: Overlay, payload: unknown) => {
  const handler = handlers[overlay];
  if (!handler) {
    return;
  }
  await handler(payload);
};

const isOverlay = (overlay: string): overlay is Overlay => {
  return Object.values(Overlays).includes(overlay as Overlay);
};
