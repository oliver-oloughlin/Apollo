/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx";
import { UnknownPageProps } from "$fresh/server.ts";

export default function Error404({ url }: UnknownPageProps) {
  return (
    <App>
      <main class="page centered-text">
        <h1>Error 404</h1>
        <p>The requested page {url} was not found</p>
      </main>
    </App>
  );
}
