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
        title: "Poll 1",
        code: "12121",
        timeToStop: "2022.09.15, 14:23",
        private: false
      },
      {
        id: 2,
        title: "Poll 2",
        code: "12345",
        timeToStop: "2023.01.1, 00:01",
        private: true
      },
      {
        id: 3,
        title: "Poll 3",
        code: "54321",
        timeToStop: "2022.12.24, 18:00",
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
