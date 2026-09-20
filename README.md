# SimpleDB Storage Engine — Coursework

Four assignments extending a teaching database engine: a buffer pool, write-ahead logging with recovery, lock-based concurrency control, and query materialisation with indexes.

**Course:** CENG435 — Database Design and Implementation  
**Institution:** İzmir Institute of Technology (IYTE) — İzmir, Türkiye

## What each assignment added

| Folder | Assignment | Implementation |
|---|---|---|
| `hw1-buffer-manager` | Buffer pool | `BufferMgr` with pin/unpin over a fixed pool of `Buffer` frames and an LRU replacement policy; `BufferTestScenario` exercises pin counts and eviction |
| `hw2-hw4-engine` | Recovery, concurrency, materialisation | `RecoveryMgr.doRecover()` rebuilt around an active-transaction set with non-quiescent checkpointing (`NQCheckpoint`); `ConcurrencyMgr` + `LockTable` for two-phase locking; `RenameScan` and `UnionScan` relational operators |

`hw2-hw4-engine` is the fullest tree and includes the earlier buffer-pool work. The
`concurrencytest/` and `*Test/` data folders are created when the tests run, so they are
not tracked.

## Setup

This is a **patch set**, not a standalone engine — the provided skeleton is not redistributed
here. See `SETUP.md` for how to fetch it and overlay these files, and `ATTRIBUTION.md` for
exactly which files are mine.

## Running

Import both projects into Eclipse, then:

| Test | Entry point |
|---|---|
| Buffer pool | `simpledb.buffer.BufferMgrTest` |
| Recovery | `simpledb.tx.recovery.HW2TestA` / `HW2TestB` |
| Concurrency | `simpledb.tx.concurrency.HW2Test` |
| Query materialisation | `simpledb.query.HW4Test` |

## Third-party components

The engine skeleton and interfaces come from *Database Design and Implementation* (Sciore, Springer 2020), companion code SimpleDB v3.4. That distribution is **not** included here; every file in this repository is my own work.

---

Submitted reports, worksheets and lecture material are archived outside this
repository rather than committed, so the repo stays code-only.
