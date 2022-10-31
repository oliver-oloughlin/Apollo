import { Fragment } from "preact"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { useMemo, useState, useRef, useEffect } from "preact/hooks"
import { Poll } from "../utils/models.ts"
import { API_HOST } from "../utils/api.ts"
import { MockPolls } from "../utils/mock-data.ts"

export default function PollsView() {
  const searchRef = useRef<HTMLInputElement>(null)
  const [search, setSearch] = useState<string>("")
  const [polls, setPolls] = useState<Poll[]>(MockPolls)

  async function fetchPolls() {
    try {
      const res = await fetch(`${API_HOST}/poll`)
      if (!res.ok) throw Error(res.statusText)
      const _polls = await res.json() as Poll[]
      setPolls(_polls)
    }
    catch (err) {
      console.error(err)
    }
  }

  useEffect(() => {
    fetchPolls()
  }, [])

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