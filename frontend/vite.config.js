import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    historyApiFallback: true, // SPA 핵심 설정: 직접 주소 접근 시 index.html로 라우팅
    proxy: {
      "/api": {
        target: "http://localhost:80",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
