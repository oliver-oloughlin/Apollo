/** @jsx h */
import { h } from "preact";
import App from "../components/App.tsx";
import { Handlers, PageProps } from "$fresh/server.ts";
import { Poll } from "../utils/models.ts";
import PollsView from "../islands/PollsView.tsx";

interface Data {
  polls: Poll[]
}

export const handler: Handlers<Data> = {
  GET: (req, ctx) => {
    const polls: Poll[] = [
      {
        id: 1,
        name: "Poll 1",
        private: false
      },
      {
        id: 2,
        name: "Poll 2",
        private: true
      },
      {
        id: 3,
        name: "Poll 3",
        private: false
      },
    ]
    return ctx.render({ polls })
  }
}

export default function Home({ data }: PageProps<Data>) {
  const { polls } = data
  return (
    <App>
      <main class="page">
        <div class="page-content">
          <PollsView polls={polls} />
        </div>
      </main>
    </App>
  );
}
