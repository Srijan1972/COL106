# DSCoin

The backend of a dummy cryptocurrency created as a part of the COL106 course
Features only single "coin" transactions

## Features

### Transaction Queue

- A linked list based data structure used to store pending transactions made by users.
- Basic queue operations- add, remove, size implemented

### Checking Transaction

- Making sure the 'coin' is present in the source given
- Checking for double spending

### Creating Transaction Blocks

- Creating a transaction block using an array of transactions
- Generating a merkle tree from the array

### Insertion into an Honest Chain

- Simple insertion into an authenticated list assuming all users (miners) to be honest

### Checking Transaction Blocks

- 1<sup>st</sup> 4 digits of nonce must be "0000" (A pseudo-random condition to force users (miners) to spend large amounts of computational resources)
- Signature of this block must have been correctly computed
- The merkle tree was generated accurately

### Insertion into a Malicious Chain

- First longest valid chain is found
- New block is inserted into the longest valid chain

### Initiate Transaction (Only for Honest Case)

- Remove the coins from the sending user
- Add the transaction to both pending transaction queue and in process array

### Finalize Transaction (Only for Honest Case)

- Check if the block containing the transaction is present in the chain
- Remove the transaction from the in process array and pending transaction queue
- Give the sibling-coupled path-to-root from the merkle tree in the block and a list containing the signatures of all the blocks after it.

### Mining

- Take **tr-count-1** valid transactions and a reward coin for the miner (incentive to be honest)
- Use these transactions to create a transaction block and add it to the blockchain

### Initiate Currency

- A **moderator** inititates the currency by giving an initial amount of coins to users in a round robin fashion
- Each one of these transactions is made into a transaction array which is made into a block to be inserted into the blockchain.
