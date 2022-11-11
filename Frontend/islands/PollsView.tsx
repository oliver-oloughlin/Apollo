import { Fragment } from "preact"
import { Style } from "fresh_utils"
import { Head } from "$fresh/runtime.ts"
import { useMemo, useState, useRef, useEffect } from "preact/hooks"
import { Poll } from "../utils/models.ts"
import { API_HOST } from "../utils/api.ts"

export default function PollsView() {
  const searchRef = useRef<HTMLInputElement>(null)
  const [search, setSearch] = useState<string>("")
  const [polls, setPolls] = useState<Poll[]>([])

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
    const interval = setInterval(fetchPolls, 30_000) // Re-fetches polls every 30 seconds
    return () => clearInterval(interval)
  }, [])

  const pollRows = useMemo(() => {
    return polls.filter(p => p.title.toLowerCase().includes(search.toLowerCase()) && !p.closed).map((poll, index) => {
      return (
        <div class="polls-row" key={`poll-${index}`} onClick={() => window.open(`/vote?code=${poll.code}`, "_self")}>
          <p class="polls-title">{poll.title}</p>
          <p>{poll.timeToStop}</p>
          <p>{poll.privatePoll ? "PRIVATE" : "PUBLIC"}</p>
        </div>
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
        <div class="polls-column">
          <div class="polls-row">
            <strong class="polls-heading">Title</strong>
            <strong class="polls-heading">Ends</strong>
            <strong class="polls-heading">Access</strong>
          </div>
          {pollRows}
        </div>
      </div>
    </Fragment>
  )
}