// DO NOT EDIT. This file is generated by fresh.
// This file SHOULD be checked into source version control.
// This file is automatically updated during development when running `dev.ts`.

import config from "./deno.json" assert { type: "json" };
import * as $0 from "./routes/_404.tsx";
import * as $1 from "./routes/index.tsx";
import * as $2 from "./routes/sign-in.tsx";
import * as $3 from "./routes/sign-up.tsx";
import * as $4 from "./routes/user/create-poll.tsx";
import * as $5 from "./routes/user/index.tsx";
import * as $6 from "./routes/user/manage-poll.tsx";
import * as $7 from "./routes/user/manage-system.tsx";
import * as $8 from "./routes/vote.tsx";
import * as $9 from "./routes/vote/[code].tsx";
import * as $$0 from "./islands/Header.tsx";
import * as $$1 from "./islands/PollsView.tsx";
import * as $$2 from "./islands/SignInForm.tsx";
import * as $$3 from "./islands/SignUpForm.tsx";

const manifest = {
  routes: {
    "./routes/_404.tsx": $0,
    "./routes/index.tsx": $1,
    "./routes/sign-in.tsx": $2,
    "./routes/sign-up.tsx": $3,
    "./routes/user/create-poll.tsx": $4,
    "./routes/user/index.tsx": $5,
    "./routes/user/manage-poll.tsx": $6,
    "./routes/user/manage-system.tsx": $7,
    "./routes/vote.tsx": $8,
    "./routes/vote/[code].tsx": $9,
  },
  islands: {
    "./islands/Header.tsx": $$0,
    "./islands/PollsView.tsx": $$1,
    "./islands/SignInForm.tsx": $$2,
    "./islands/SignUpForm.tsx": $$3,
  },
  baseUrl: import.meta.url,
  config,
};

export default manifest;
