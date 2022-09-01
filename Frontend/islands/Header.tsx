/** @jsx h */
import { h, Fragment } from "preact";
import { useState } from "preact/hooks"

export default function Header() {
  const [open, setOpen] = useState<boolean>(false);

  return (
    <header>
      <div class="header-content-horisontal">
        <Items />
      </div>
      <div class="header-content-vertical">
        <div onClick={() => setOpen(o => !o)} class="header-dropdown-btn icon" />
        {open &&
          <div class="header-dropdown-container">
            <Items />
          </div>
        }
      </div>
    </header>
  )
}

function Items() {
  return (
    <Fragment>
      <p class="header-item">POLLS</p>
      <p class="header-item">EXAMPLE</p>
      <p class="header-item">OTHER ITEM</p>
    </Fragment>
  )
}