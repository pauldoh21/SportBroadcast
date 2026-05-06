// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2025-07-15",
  devtools: { enabled: true },

  vite: {
    optimizeDeps: {
      include: ["gsap"],
    },
  },

  nitro: {
    experimental: {
      websocket: true,
    },
  },

  typescript: {
    typeCheck: true,
  },

  modules: ["@vueuse/nuxt", "@nuxt/fonts"],

  fonts: {
    families: [
      { name: "DM Serif Text", provider: "local" },
      { name: "Haettenschweiler", provider: "local" },
    ],
  },
});
