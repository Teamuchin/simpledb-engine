# Attribution

## What I did not write

The skeleton of this system — the package layout, the interfaces, most of the implementation
and the test harness — was provided as course material. It is the `SimpleDB` database engine
written by:

> **Sciore, E. (2020). _Database Design and Implementation_. Springer.**
> Companion code: **SimpleDB v3.4**, released 24 March 2021 and distributed by the author
> (contact: sciore@bc.edu).

That distribution is **not included in this repository.** Every file here is either my own work
or an Eclipse project file I created; the provided code is expected to be fetched separately
(see `SETUP.md`). This repository is a **patch set**, not a standalone copy of the engine.

## What I did write

The split below was established by hashing every file against the pristine SimpleDB v3.4
distribution: of 157 engine source files in the first tree and 164 in the second, 152 and 144
respectively are byte-identical to the provided skeleton and were removed. The remaining diff
was read by hand to confirm the changes are coursework rather than a version mismatch.

### `hw1-buffer-manager/src/simpledb/` — buffer pool

| File | |
|---|---|
| `buffer/BufferMgr.java` | modified — pin/unpin accounting and LRU eviction over a fixed pool |
| `buffer/Buffer.java` | modified |
| `buffer/BufferMgrTest.java` | modified — extended pin/unpin scenario, 4 buffers, `printStatus()` |
| `buffer/BufferTestScenario.java` | **new** — additional pin-count and eviction cases |
| `file/Page.java` | modified — bounds checking on every read and write |
| `file/FileTest.java` | modified |

### `hw2-hw4-engine/src/simpledb/` — recovery, concurrency, materialisation

| File | |
|---|---|
| `tx/recovery/RecoveryMgr.java` | modified — `doRecover()` rewritten around an active-transaction set; added a non-quiescent `checkpoint(List<Integer>)` |
| `tx/recovery/NQCheckpoint.java` | **new** — non-quiescent checkpoint log record |
| `tx/recovery/LogRecord.java` | modified |
| `tx/recovery/HW2TestA.java`, `HW2TestB.java` | **new** — recovery test drivers |
| `tx/concurrency/ConcurrencyMgr.java` | modified |
| `tx/concurrency/LockTable.java` | modified |
| `tx/concurrency/HW2Test.java` | **new** |
| `tx/Transaction.java` | modified |
| `query/RenameScan.java` | **new** — relational rename operator |
| `query/UnionScan.java` | **new** — relational union operator |
| `query/HW4Test.java` | **new** |
| `record/Layout.java`, `RecordPage.java`, `TableScan.java` | modified |
| `buffer/*`, `file/*` | modified (carried forward from the first tree) |
