/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx";
import Style from "../components/Style.tsx";
import { Head } from "$fresh/runtime.ts";

export default function Home() {
  return (
    <App>
      <main>
        Home Page
      </main>
    </App>
  );
}
