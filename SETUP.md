# Setup — how to build this repository

This repo is a **patch set**. It does not contain the SimpleDB skeleton it was built on, because
that code belongs to the course and to its author. Fetch it first, then overlay.

## 1. Get the base

Download the **SimpleDB v3.4** distribution — the companion code to:

> Sciore, E. (2020). *Database Design and Implementation*. Springer.

It ships as a WinZip archive containing `simpledb/`, `simpleclient/`, `derbyclient/`,
`BookErrata.pdf` and a README.

## 2. Create the Eclipse workspace

1. Create a Java project named `SimpleDBEngine`, and a second named `SimpleDBClients` with
   `SimpleDBEngine` on its build path.
2. Copy the base `simpledb/` folder into `SimpleDBEngine/src/`.
3. Copy the base `simpleclient/` folder into `SimpleDBClients/src/`.

## 3. Overlay this repository

Each folder's `src/` mirrors the Eclipse project's `src/`, so copy it over and overwrite:

```bash
cp -r hw2-hw4-engine/src/* SimpleDBEngine/src/
```

`hw2-hw4-engine` is a superset of `hw1-buffer-manager`, so overlaying it alone gives the
complete final state.

## 4. Run

| What | Entry point |
|---|---|
| Start the server | `simpledb.server.StartServer` (arg = database folder, default `studentdb`) |
| Create the sample database | `simpleclient.CreateStudentDB` |
| Buffer-pool test | `simpledb.buffer.BufferMgrTest` |
| Recovery test | `simpledb.tx.recovery.HW2TestA` / `HW2TestB` |
| Concurrency test | `simpledb.tx.concurrency.HW2Test` |
| Query test | `simpledb.query.HW4Test` |

The `studentdb/`, `concurrencytest/` and `*Test/` data folders are created at run time — they
are deliberately not in this repository.
