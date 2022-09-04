/** @jsx h */
import { Fragment, h } from "preact";
import { useState } from "preact/hooks";

export default function Header() {
  const [open, setOpen] = useState<boolean>(false);

  return (
    <header>
      <div class="header-content-horisontal">
        <Items />
      </div>
      <div class="header-content-vertical">
        <span class="header-dropdown-btn" onClick={() => setOpen((o) => !o)}>
          <i class="icon menu-icon" />
        </span>
        {open &&
          <div class="header-dropdown-container">
            <Items />
          </div>
        }
      </div>
    </header>
  );
}

function Items() {
  return (
    <Fragment>
      <p class="header-item">POLLS</p>
      <p class="header-item">RESULT</p>
      <p class="header-item">VOTES</p>
      <p class="header-item">ETC.</p>
    </Fragment>
  );
}
