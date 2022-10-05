
import { Fragment } from "preact"
import { useState, useEffect, useRef } from "preact/hooks"

export default function Header() {
  const [open, setOpen] = useState<boolean>(false);
  const dropDownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const closeOnClickOutside = (e: any) => {
      if (!dropDownRef.current?.contains(e.target)) setOpen(false)
    }
    
    window.addEventListener("pointerdown", closeOnClickOutside)
    return () => window.removeEventListener("pointerdown", closeOnClickOutside)
  }, [setOpen]);

  return (
    <header>
      <div class="header-content-horisontal">
        <Items />
      </div>
      <div ref={dropDownRef} class="header-content-vertical">
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
      <a href="/" class="header-item">HOME</a>
      <a href="/user" class="header-item">ACCOUNT</a>
    </Fragment>
  );
}
