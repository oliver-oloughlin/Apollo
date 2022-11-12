// DO NOT EDIT. This file is generated by fresh.
// This file SHOULD be checked into source version control.
// This file is automatically updated during development when running `dev.ts`.

import config from "./deno.json" assert { type: "json" };
import * as $0 from "./routes/_404.tsx";
import * as $1 from "./routes/index.tsx";
import * as $2 from "./routes/sign-in.tsx";
import * as $3 from "./routes/sign-up.tsx";
import * as $4 from "./routes/user/index.tsx";
import * as $5 from "./routes/user/poll.tsx";
import * as $6 from "./routes/vote.tsx";
import * as $$0 from "./islands/AppState.tsx";
import * as $$1 from "./islands/Header.tsx";
import * as $$2 from "./islands/ManagePollView.tsx";
import * as $$3 from "./islands/PollsView.tsx";
import * as $$4 from "./islands/SignInForm.tsx";
import * as $$5 from "./islands/SignUpForm.tsx";
import * as $$6 from "./islands/UserView.tsx";
import * as $$7 from "./islands/VoteView.tsx";

const manifest = {
  routes: {
    "./routes/_404.tsx": $0,
    "./routes/index.tsx": $1,
    "./routes/sign-in.tsx": $2,
    "./routes/sign-up.tsx": $3,
    "./routes/user/index.tsx": $4,
    "./routes/user/poll.tsx": $5,
    "./routes/vote.tsx": $6,
  },
  islands: {
    "./islands/AppState.tsx": $$0,
    "./islands/Header.tsx": $$1,
    "./islands/ManagePollView.tsx": $$2,
    "./islands/PollsView.tsx": $$3,
    "./islands/SignInForm.tsx": $$4,
    "./islands/SignUpForm.tsx": $$5,
    "./islands/UserView.tsx": $$6,
    "./islands/VoteView.tsx": $$7,
  },
  baseUrl: import.meta.url,
  config,
};

export default manifest;
