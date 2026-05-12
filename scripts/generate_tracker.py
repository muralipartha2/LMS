"""
generate_tracker.py
-------------------
Scans all problem folders in the repo and auto-generates REVISION_TRACKER.md.
- Preserves existing revision status and dates for already tracked problems
- New problems default to ⬜ with empty date
- Run locally or via GitHub Action
"""

import os
import re
from datetime import date

# ─── Configuration ────────────────────────────────────────────────────────────

# Add new folders here as you expand beyond arrays
PROBLEM_FOLDERS = [
    "Array_problems",
    "Stack_problems",
    "Queue_problems",
    "PriorityQueue_problems"
    # "String_problems",
    # "LinkedList_problems",
    # "Tree_problems",
    # "DP_problems",
]

TRACKER_FILE = "REVISION_TRACKER.md"

# Pattern classification rules — matched against filename (case insensitive)
# Order matters — first match wins
PATTERN_RULES = [
    ("Sliding Window + Deque",      ["slidingwindow", "maximumsliding", "firstnegative"]),
    ("Fixed Sliding Window",        ["maxavg", "maxvowels", "permutation", "allanagram", "maximumsubarraysumofsizek", "maximumsumofdistrinct"]),
    ("Variable Sliding Window",     ["longestsubstring", "minimizesize", "maxconsecutiveonesiii", "maximizenumberof", "longestsubstringwithk"]),
    ("Prefix Sum + HashMap",        ["continuoussubarray", "subarraysumequals"]),
    ("Prefix Sum 2D",               ["2d", "2dimmutable"]),
    ("Prefix Sum",                  ["runningsum", "rangesum", "highestaltitude", "middleindex", "maximumscore", "pivotindex"]),
    ("Two Pointer + Greedy",        ["boats"]),
    ("Two Pointer",                 ["container", "twosum", "mergesorted", "countpairs", "removeelement", "removeduplicates", "movezeroes", "trapping"]),
    ("Boyer-Moore / HashMap",       ["majorityelement"]),
    ("HashMap + Sorting",           ["rankteams"]),
    ("HashMap + Greedy",            ["reducearraysize"]),
    ("HashMap",                     ["validanagram", "twosum", "intersection", "containsduplicate"]),
    ("HashSet",                     ["containsduplicate", "intersection"]),
    ("Stack + Circular",            ["nextgreaterelement_ii"]),
    ("Stack",                       ["nextgreater"]),
    ("Greedy / Single Pass",        ["longestcontinuous"]),
    ("Greedy",                      ["besttimetobuy", "mergeintervals", "insertintervals"]),
    ("Kadane's adapted",            ["maximumproductsubarray"]),
    ("Kadane's",                    ["maximumsubarray"]),
    ("Matrix + Boundary Simulation",["spiralmatrix", "spiral"]),
    ("Matrix + Transpose + Reverse",["rotateimage"]),
    ("Matrix Traversal",            ["xmatrix", "checkifmatrix"]),
    ("Sorting",                     ["minimumabsolute"]),
    ("Prefix + Suffix",             ["productofarray"]),
    ("Basic",                       ["concatenation", "shufflethe", "plusone", "mergestrings", "pascals", "missingranges", "summaryranges", "containsduplicateiii"]),
]

UPCOMING_GAPS = """
## Upcoming Problems to Add (Gaps Identified)

These patterns are missing from current coverage and should be added gradually:

**Binary Search on Arrays**
- Search in Rotated Sorted Array
- Find Minimum in Rotated Sorted Array
- Koko Eating Bananas

**Sorting Based**
- 3Sum
- 4Sum
- Sort Colors (Dutch National Flag)

**Important Standalone**
- Longest Consecutive Sequence
- Jump Game I
- Jump Game II
- Gas Station
- Find the Duplicate Number
- Maximum Circular Subarray Sum

**Matrix — Missing**
- Set Matrix Zeroes
- Search a 2D Matrix
"""

# ─── Helpers ──────────────────────────────────────────────────────────────────

def classify_pattern(filename: str) -> str:
    """Match filename against pattern rules."""
    name = filename.lower().replace("_", "").replace("-", "").replace(".java", "")
    for pattern, keywords in PATTERN_RULES:
        for kw in keywords:
            if kw.lower().replace("_", "") in name:
                return pattern
    return "Miscellaneous"


def parse_existing_tracker(filepath: str) -> dict:
    """
    Read existing tracker and extract {problem_key: (status, last_revised, notes)}.
    problem_key = lowercase filename without extension.
    """
    existing = {}
    if not os.path.exists(filepath):
        return existing

    row_re = re.compile(
        r"\|\s*\d+\s*\|\s*(.+?)\s*\|\s*(.+?)\s*\|\s*([⬜✅🔄])\s*\|\s*(.*?)\s*\|\s*(.*?)\s*\|"
    )
    with open(filepath, encoding="utf-8") as f:
        for line in f:
            m = row_re.match(line.strip())
            if m:
                name_raw = m.group(1).strip()
                status   = m.group(3).strip()
                revised  = m.group(4).strip()
                notes    = m.group(5).strip()
                key = re.sub(r"[^a-z0-9]", "", name_raw.lower())
                existing[key] = (status, revised, notes)
    return existing


def scan_problems(folder: str) -> list[dict]:
    """Return sorted list of problem dicts from a folder."""
    if not os.path.isdir(folder):
        return []

    problems = []
    skip = {"sometips", ".ds_store", "readme.md", ".gitkeep"}

    for fname in sorted(os.listdir(folder)):
        if fname.lower() in skip or fname.startswith("."):
            continue

        # Extract number and display name
        m = re.match(r"^(\d+)[_\-](.+?)(?:\.(java|py|js|ts|md))?$", fname, re.IGNORECASE)
        if not m:
            continue

        num      = int(m.group(1))
        raw_name = m.group(2)
        display  = raw_name.replace("_", " ").replace("-", " ").title()
        pattern  = classify_pattern(fname)
        key      = re.sub(r"[^a-z0-9]", "", display.lower())

        problems.append({
            "num":     num,
            "fname":   fname,
            "display": display,
            "pattern": pattern,
            "key":     key,
            "folder":  folder,
        })

    return problems


def build_table(problems: list[dict], existing: dict) -> str:
    """Build markdown table rows for a list of problems."""
    if not problems:
        return "_No problems found._\n"

    header = "| # | Problem | Pattern | Revised | Last Revised | Notes |\n"
    sep    = "|---|---|---|---|---|---|\n"
    rows   = []

    for p in problems:
        status, revised, notes = existing.get(p["key"], ("⬜", "—", ""))
        rows.append(
            f"| {p['num']:03d} | {p['display']} | {p['pattern']} "
            f"| {status} | {revised} | {notes} |"
        )

    return header + sep + "\n".join(rows) + "\n"


def group_by_pattern(problems: list[dict]) -> dict[str, list]:
    """Group problems by their pattern."""
    groups: dict[str, list] = {}
    for p in problems:
        groups.setdefault(p["pattern"], []).append(p)
    return groups


def build_summary(all_problems: list[dict], existing: dict) -> str:
    """Build progress summary table."""
    groups = group_by_pattern(all_problems)
    lines  = [
        "| Pattern | Total | ✅ Revised | ⬜ Remaining |",
        "|---|---|---|---|",
    ]
    grand_total = grand_done = 0
    for pattern, probs in sorted(groups.items()):
        total = len(probs)
        done  = sum(
            1 for p in probs
            if existing.get(p["key"], ("⬜",))[0] == "✅"
        )
        lines.append(f"| {pattern} | {total} | {done} | {total - done} |")
        grand_total += total
        grand_done  += done

    lines.append(
        f"| **Total** | **{grand_total}** | **{grand_done}** | **{grand_total - grand_done}** |"
    )
    return "\n".join(lines) + "\n"


# ─── Main ─────────────────────────────────────────────────────────────────────

def main():
    existing = parse_existing_tracker(TRACKER_FILE)

    all_problems: list[dict] = []
    folder_sections: list[tuple[str, list[dict]]] = []

    for folder in PROBLEM_FOLDERS:
        probs = scan_problems(folder)
        all_problems.extend(probs)
        folder_sections.append((folder, probs))

    today = date.today().strftime("%B %Y")

    lines = [
        "# DSA Problems — Revision Tracker",
        "",
        "> Auto-generated by `scripts/generate_tracker.py` — do not edit tables manually.",
        "> Update **Revised** (⬜/✅/🔄) and **Last Revised** date directly in this file after each revision session.",
        "",
        "## Status Legend",
        "| Symbol | Meaning |",
        "|---|---|",
        "| ⬜ | Not yet revised |",
        "| ✅ | Revised |",
        "| 🔄 | Needs another pass |",
        "",
        "---",
        "",
        "## Revision Schedule",
        "| Week | Problems to Revise |",
        "|---|---|",
        "| Week 1 | Problems 001 – 020 |",
        "| Week 2 | Problems 021 – 040 |",
        "| Week 3 | Problems 041 – 060 |",
        "| Week 4 | Problems 061 onwards |",
        "| Week 5+ | Cycle back from 001 |",
        "",
        "---",
        "",
    ]

    for folder, problems in folder_sections:
        folder_title = folder.replace("_", " ")
        lines.append(f"## {folder_title}")
        lines.append("")

        groups = group_by_pattern(problems)
        for pattern in sorted(groups.keys()):
            lines.append(f"### {pattern}")
            lines.append("")
            lines.append(build_table(groups[pattern], existing))

    lines += [
        "---",
        "",
        "## Revision Progress Summary",
        "",
        build_summary(all_problems, existing),
        "",
        "---",
        "",
        UPCOMING_GAPS.strip(),
        "",
        "---",
        "",
        f"*Last auto-generated: {today}*",
        "",
    ]

    with open(TRACKER_FILE, "w", encoding="utf-8") as f:
        f.write("\n".join(lines))

    print(f"✅ REVISION_TRACKER.md updated — {len(all_problems)} problems tracked.")


if __name__ == "__main__":
    main()
