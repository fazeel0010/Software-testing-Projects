#!/usr/bin/env python3
"""Clean saved HTML page sources for LLM assertion-generation experiments.

Removes JavaScript and CSS artifacts from .html files:
- <script> blocks
- <style> blocks
- stylesheet <link> tags
- inline style attributes
- inline JS event handler attributes (onclick, onload, ...)
- javascript: URLs in href/src

Usage examples:
  python scripts/clean_page_sources.py
  python scripts/clean_page_sources.py --dir page-sources
  python scripts/clean_page_sources.py --check
"""

from __future__ import annotations

import argparse
import re
from pathlib import Path


SCRIPT_RE = re.compile(r"<script\b[^>]*>.*?</script\s*>", re.IGNORECASE | re.DOTALL)
STYLE_TAG_RE = re.compile(r"<style\b[^>]*>.*?</style\s*>", re.IGNORECASE | re.DOTALL)
STYLESHEET_LINK_RE = re.compile(
    r"<link\b(?=[^>]*\brel\s*=\s*\"stylesheet\")[^>]*>",
    re.IGNORECASE,
)
CSS_LINK_RE = re.compile(
    r"<link\b(?=[^>]*\bhref\s*=\s*\"[^\"]*\.css(?:\?[^\"]*)?\")[^>]*>",
    re.IGNORECASE,
)
STYLE_ATTR_RE = re.compile(r"\sstyle\s*=\s*\"[^\"]*\"", re.IGNORECASE)
ON_ATTR_RE = re.compile(r"\son[a-zA-Z]+\s*=\s*\"[^\"]*\"", re.IGNORECASE)
JS_URL_RE = re.compile(
    r"(\s(?:href|src)\s*=\s*\")\s*javascript:[^\"]*(\")",
    re.IGNORECASE,
)


CHECK_PATTERNS = {
    "script tag": re.compile(r"<script\b|</script>", re.IGNORECASE),
    "style tag": re.compile(r"<style\b|</style>", re.IGNORECASE),
    "stylesheet link": re.compile(r"rel\s*=\s*\"stylesheet\"|\.css", re.IGNORECASE),
    "inline style attr": re.compile(r"\sstyle\s*=\s*\"", re.IGNORECASE),
}


def clean_html(text: str) -> str:
    cleaned = text
    cleaned = SCRIPT_RE.sub("", cleaned)
    cleaned = STYLE_TAG_RE.sub("", cleaned)
    cleaned = STYLESHEET_LINK_RE.sub("", cleaned)
    cleaned = CSS_LINK_RE.sub("", cleaned)
    cleaned = STYLE_ATTR_RE.sub("", cleaned)
    cleaned = ON_ATTR_RE.sub("", cleaned)
    cleaned = JS_URL_RE.sub(r"\1\2", cleaned)
    return cleaned


def file_has_artifacts(text: str) -> list[str]:
    found = []
    for label, pattern in CHECK_PATTERNS.items():
        if pattern.search(text):
            found.append(label)
    return found


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Clean JS/CSS from saved HTML page sources.")
    parser.add_argument(
        "--dir",
        default="html",
        help="Directory containing .html page-source files (default: page-sources)",
    )
    parser.add_argument(
        "--check",
        action="store_true",
        help="Check only; do not modify files. Exits non-zero when artifacts are found.",
    )
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    directory = Path(args.dir)

    if not directory.exists() or not directory.is_dir():
        print(f"error: directory not found: {directory}")
        return 2

    html_files = sorted(directory.glob("*.html"))
    if not html_files:
        print(f"no .html files found in {directory}")
        return 0

    changed = 0
    clean = 0
    artifact_files = 0

    for path in html_files:
        original = path.read_text(encoding="utf-8")
        artifacts = file_has_artifacts(original)

        if artifacts:
            artifact_files += 1

        if args.check:
            if artifacts:
                print(f"needs-cleaning: {path.name} ({', '.join(artifacts)})")
            else:
                print(f"ok: {path.name}")
            continue

        cleaned = clean_html(original)
        if cleaned != original:
            path.write_text(cleaned, encoding="utf-8")
            changed += 1
            print(f"cleaned: {path.name}")
        else:
            clean += 1
            print(f"unchanged: {path.name}")

    if args.check:
        print(
            f"check-summary: total={len(html_files)}, clean={len(html_files) - artifact_files}, "
            f"needs-cleaning={artifact_files}"
        )
        return 1 if artifact_files else 0

    print(f"clean-summary: total={len(html_files)}, cleaned={changed}, unchanged={clean}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
