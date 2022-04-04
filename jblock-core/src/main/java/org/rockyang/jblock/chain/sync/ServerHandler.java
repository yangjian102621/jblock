package org.rockyang.jblock.chain.sync;

import org.apache.commons.lang3.StringUtils;
import org.rockyang.jblock.chain.Block;
import org.rockyang.jblock.chain.Message;
import org.rockyang.jblock.chain.service.ChainService;
import org.rockyang.jblock.miner.pow.ProofOfWork;
import org.rockyang.jblock.net.base.MessagePacket;
import org.rockyang.jblock.net.base.MessagePacketType;
import org.rockyang.jblock.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * message response handler
 * reply the message that send from the other client
 * @author yangjian
 */
@Component
public class ServerHandler {

	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	private final ChainService chainService;

	public ServerHandler(ChainService chainService)
	{
		this.chainService = chainService;
	}

	/**
	 * 去人确认交易
	 * @param body
	 */
	public MessagePacket confirmTransaction(byte[] body) throws Exception {

		RespVo responseVo = new RespVo();
		MessagePacket resPacket = new MessagePacket();
		Message tx = (Message) SerializeUtils.unSerialize(body);
		logger.info("收到交易确认请求， {}", tx);
		responseVo.setItem(tx);
//		//验证交易
//		if (Sign.verify(Keys.publicKeyDecode(tx.getPublicKey()), tx.getSign(), tx.toSignString())) {
//			responseVo.setSuccess(true);
//			//将交易放入交易池
//			messagePool.addTransaction(tx);
//		} else {
//			responseVo.setSuccess(false);
//			responseVo.setMessage("交易签名错误");
//			logger.info("交易确认失败, 交易签名错误, {}", tx);
//		}
		resPacket.setType(MessagePacketType.RES_CONFIRM_TRANSACTION);
		resPacket.setBody(SerializeUtils.serialize(responseVo));

		return resPacket;
	}

	/**
	 * 获取下一个区块
	 * @param body
	 * @return
	 */
	public MessagePacket fetchNextBlock(byte[] body) {

		RespVo responseVo = new RespVo();
		MessagePacket resPacket = new MessagePacket();
		Integer blockIndex = (Integer) SerializeUtils.unSerialize(body);
		logger.info("收到区块同步请求, 同步区块高度为， {}", blockIndex);
//		Optional<Block> block = dataStore.getBlock(blockIndex);
//		if (block.isPresent()) {
//			responseVo.setItem(block.get());
//			responseVo.setSuccess(true);
//		} else {
//			responseVo.setSuccess(false);
//			responseVo.setItem(null);
//			responseVo.setMessage("要同步的区块不存在.{"+blockIndex+"}");
//		}
//		resPacket.setType(MessagePacketType.RES_SYNC_NEXT_BLOCK);
//		resPacket.setBody(SerializeUtils.serialize(responseVo));

		return resPacket;
	}

	/**
	 * new block event handler
	 */
	public MessagePacket newBlock(byte[] body) throws Exception {

		RespVo respVo = new RespVo();
		MessagePacket resPacket = new MessagePacket();
		Block newBlock = (Block) SerializeUtils.unSerialize(body);
		logger.info("receive new block confirm request： {}", newBlock);
		if (checkBlock(newBlock, respVo)) {
			respVo.setSuccess(true);
			chainService.saveBlock(newBlock);
		} else {
			logger.error("block confirmation failed：{}", respVo.getMessage());
			respVo.setSuccess(false);
		}
		resPacket.setType(MessagePacketType.RES_NEW_BLOCK);
		resPacket.setBody(SerializeUtils.serialize(respVo));

		return resPacket;
	}

	/**
	 * check the block
	 * 1. Check if the previous block is exists, and previousHash is correct
	 * 2. Check if the pow result
	 * 3. Check if the block signature is correct
	 */
	public boolean checkBlock(Block block, RespVo respVo) {

		// @TODO: check the genesis block

		// check the proof of work nonce
		ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(block.getHeader());
		if (!proofOfWork.validate()) {
			respVo.setMessage("Invalid Pow result");
			return false;
		}

		// check the prev block
		if (block.getHeader().getHeight() > 1) {
			Block prevBlock = chainService.getBlock(block.getHeader().getHeight()-1);
			if (prevBlock == null || !StringUtils.equals(prevBlock.getHeader().getHash(), block.getHeader().getPreviousHash())) {
				respVo.setMessage("Invalid previous hash");
				return false;
			}
		}

		// @TODO: check the block signature

		return true;

	}

	public MessagePacket getNodeList(byte[] body)
	{
		String message = (String) SerializeUtils.unSerialize(body);
		RespVo responseVo = new RespVo();
		MessagePacket resPacket = new MessagePacket();
		logger.info("收到获取节点列表请求");
//		if (Objects.equal(message, MessagePacket.FETCH_NODE_LIST_SYMBOL)) {
//			Optional<List<Node>> nodes = dataStore.getNodeList();
//			if (nodes.isPresent()) {
//				responseVo.setSuccess(true);
//				responseVo.setItem(nodes.get());
//			}
//		} else {
//			responseVo.setSuccess(false);
//		}
		resPacket.setType(MessagePacketType.RES_NODE_LIST);
		resPacket.setBody(SerializeUtils.serialize(responseVo));

		return  resPacket;
	}

}
