/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx";
import SignInForm from "../islands/SignInForm.tsx";

export default function Home() {
  return (
    <App>
      <main class="page">
        <SignInForm />
      </main>
    </App>
  );
}
