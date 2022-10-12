
import { Fragment } from "preact"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { useMemo, useState, useRef } from "preact/hooks"
import { Poll } from "../utils/models.ts"

export default function PollsView({ polls }: { polls: Poll[] }) {
  const searchRef = useRef<HTMLInputElement>(null)
  const [search, setSearch] = useState<string>("")

  const PollRows = useMemo(() => {
    return polls.filter(p => p.title.toLowerCase().includes(search.toLowerCase()) && !p.closed).map((poll, index) => {
      return (
        <tr class="poll-row" key={`poll-${index}`} onClick={() => window.open(`/vote?code=${poll.code}`, "_self")}>
          <td>{poll.title}</td>
          <td>{poll.timeToStop}</td>
          <td>{poll.closed ? "CLOSED" : "OPEN"}</td>
          <td>{poll.privatePoll ? "PRIVATE" : "PUBLIC"}</td>
        </tr>
      )
    })
  }, [search, polls])

  return (
    <Fragment>
      <Head>
        <Style fileName="polls.css" />
      </Head>
      <div class="polls-container">
        <input
          type="text"
          placeholder="Search Poll"
          onInput={() => setSearch(searchRef.current!.value)}
          ref={searchRef}
        />
        <table class="poll-table">
          <tr>
            <th>Title</th>
            <th>Ends</th>
            <th>Status</th>
            <th>Access</th>
          </tr>
          {PollRows}
        </table>
      </div>
    </Fragment>
  )
}