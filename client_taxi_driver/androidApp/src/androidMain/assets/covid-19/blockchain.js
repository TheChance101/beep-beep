// the script for blockchain app here
const sha256 = require('sha256');

function blockchain() {
        this.chain=[]
        this.pending=[]
};
blockchain.prototype.createBlock=function(nonce, prevHash, hash){
        const newBlock = {
            index:this.chain.length+1,
            timestamp:Date.now(),
            ventilators:this.pending,
            nonce:nonce,
            prevHash:prevHash,
            hash:hash
        };
        this.pending=[]
        this.chain.push(newBlock);
};
blockchain.prototype.getLastBlock = function() {
	return this.chain[this.chain.length - 1];
};
blockchain.prototype.hash = function(prevHash, blockData, nonce){
    var hash = prevHash + JSON.stringify(blockData)+ nonce.toString() ;
    hash = sha256(hash)
    return hash;
};
blockchain.prototype.createVentilator = function(hospid, hospitalname,vacant, occupied, longi,lati,vbed,obed) {
	const newVentilator = {
        hospid: hospid,
        hospitalname:hospitalname,
		vacant: vacant,
        occupied: occupied,
        longi:longi,
        lati:lati,
        vbed:vbed,
        obed:obed
    };
    this.pending.push(newVentilator)
	return newVentilator;
};
blockchain.prototype.proofOfWork = function(prevHash, blockData) {
	let nonce = 0;
	let hash = this.hash(prevHash, blockData, nonce);
	while (hash.substring(0, 4) !== '0000') {
		nonce++;
		hash = this.hash(prevHash, blockData, nonce);
	}

	return nonce;
};

blockchain.prototype.chainIsValid = function(blockchain) {
	let validChain = true;

	for (var i = 1; i < blockchain.length; i++) {
		const currentBlock = blockchain[i];
		const prevBlock = blockchain[i - 1];
        const blockHash = this.hash(prevBlock['hash'], { ventilators: currentBlock['ventilators'], index: currentBlock['index'] }, currentBlock['nonce']);
        if (currentBlock['prevHash'] !== prevBlock['hash']) validChain = false;
	};

    const genesisBlock = blockchain[0];
    const correctNonce = genesisBlock['nonce'] === 100;
    const correctPreviousBlockHash = genesisBlock['prevHash'] === '0';
    const correctHash = genesisBlock['hash'] === '0';
    const correctTransactions = genesisBlock['ventilators'].length === 0;
	if (!correctNonce || !correctPreviousBlockHash || !correctHash || !correctTransactions) validChain = false;

	return validChain;
};
blockchain.prototype.getBlock = function(blockHash) {
	let correctBlock = null;
	this.chain.forEach(block => {
		if (block.hash === blockHash) correctBlock = block;
	});
	return correctBlock;
};

module.exports= blockchain;