import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    historyApiFallback: true,
    proxy: {
      "/auth": {
        target: "http://gateway-server:8000",
        changeOrigin: true,
      },
      "/user": {
        target: "http://gateway-server:8000",
        changeOrigin: true,
      },
      "/camera": {
        target: "http://gateway-server:8000",
        changeOrigin: true,
      },
    },
  },
});
