package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class LisztContract extends Contract {
  private static final String BINARY =
      "608060405234801561001057600080fd5b50610408806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063702bf07a1461003b578063e11349861461020a575b600080fd5b610208600480360360e081101561005157600080fd5b810190602081018135600160201b81111561006b57600080fd5b82018360208201111561007d57600080fd5b803590602001918460018302840111600160201b8311171561009e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156100f057600080fd5b82018360208201111561010257600080fd5b803590602001918460018302840111600160201b8311171561012357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929584359560ff602087013581169660408101359091169550606081013594509192509060a081019060800135600160201b81111561019457600080fd5b8201836020820111156101a657600080fd5b803590602001918460018302840111600160201b831117156101c757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506102ca945050505050565b005b6102b86004803603604081101561022057600080fd5b60ff8235169190810190604081016020820135600160201b81111561024457600080fd5b82018360208201111561025657600080fd5b803590602001918460018302840111600160201b8311171561027757600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061035b945050505050565b60408051918252519081900360200190f35b8260ff168460ff161461035257816000808660ff1660ff168152602001908152602001600020826040518082805190602001908083835b602083106103205780518252601f199092019160209182019101610301565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209290925550505b50505050505050565b60ff8216600090815260208181526040808320905184519192859282918401908083835b6020831061039e5780518252601f19909201916020918201910161037f565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220549594505050505056fea265627a7a72305820a738dfb62f7752a08a101eae6ba8c74c21b4b9b5701a5f839b6d0aec73af225764736f6c63430005090032";

  public static final String FUNC_UPDATELOCKDONE = "updateLockDone";

  public static final String FUNC_LOCKTIMEOUT = "lockTimeout";

  @Deprecated
  protected LisztContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected LisztContract(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected LisztContract(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected LisztContract(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteCall<TransactionReceipt> updateLockDone(
      String from,
      String to,
      BigInteger amount,
      BigInteger sourceRollupId,
      BigInteger targetRollupId,
      BigInteger timeout,
      String hash) {
    final Function function =
        new Function(
            FUNC_UPDATELOCKDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Utf8String(from),
                new org.web3j.abi.datatypes.Utf8String(to),
                new org.web3j.abi.datatypes.generated.Uint256(amount),
                new org.web3j.abi.datatypes.generated.Uint8(sourceRollupId),
                new org.web3j.abi.datatypes.generated.Uint8(targetRollupId),
                new org.web3j.abi.datatypes.generated.Uint256(timeout),
                new org.web3j.abi.datatypes.Utf8String(hash)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<BigInteger> lockTimeout(BigInteger rollupId, String hash) {
    final Function function =
        new Function(
            FUNC_LOCKTIMEOUT,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint8(rollupId),
                new org.web3j.abi.datatypes.Utf8String(hash)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  @Deprecated
  public static LisztContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new LisztContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static LisztContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new LisztContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static LisztContract load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new LisztContract(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static LisztContract load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new LisztContract(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<LisztContract> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        LisztContract.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<LisztContract> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(
        LisztContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<LisztContract> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        LisztContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<LisztContract> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        LisztContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }
}
