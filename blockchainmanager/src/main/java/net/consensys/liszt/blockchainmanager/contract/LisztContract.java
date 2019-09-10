package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
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
      "608060405234801561001057600080fd5b506108f1806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80635fd3bf9314610051578063cba07601146100a0578063da3d201d146103a8578063f058bf621461061c575b600080fd5b61008a6004803603604081101561006757600080fd5b81019080803560ff16906020019092919080359060200190929190505050610684565b6040518082815260200191505060405180910390f35b6103a6600480360360a08110156100b657600080fd5b81019080803590602001906401000000008111156100d357600080fd5b8201836020820111156100e557600080fd5b8035906020019184602083028401116401000000008311171561010757600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561016757600080fd5b82018360208201111561017957600080fd5b8035906020019184602083028401116401000000008311171561019b57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156101fb57600080fd5b82018360208201111561020d57600080fd5b8035906020019184602083028401116401000000008311171561022f57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561028f57600080fd5b8201836020820111156102a157600080fd5b803590602001918460208302840111640100000000831117156102c357600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561032357600080fd5b82018360208201111561033557600080fd5b8035906020019184602083028401116401000000008311171561035757600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192905050506106b8565b005b61061a600480360360808110156103be57600080fd5b81019080803590602001906401000000008111156103db57600080fd5b8201836020820111156103ed57600080fd5b8035906020019184602083028401116401000000008311171561040f57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561046f57600080fd5b82018360208201111561048157600080fd5b803590602001918460208302840111640100000000831117156104a357600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561050357600080fd5b82018360208201111561051557600080fd5b8035906020019184602083028401116401000000008311171561053757600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561059757600080fd5b8201836020820111156105a957600080fd5b803590602001918460208302840111640100000000831117156105cb57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192905050506107e8565b005b6106556004803603604081101561063257600080fd5b81019080803560ff16906020019092919080359060200190929190505050610872565b604051808581526020018481526020018381526020018215151515815260200194505050505060405180910390f35b60008060008460ff1660ff168152602001908152602001600020600083815260200190815260200160002054905092915050565b60008090505b85518110156107e05760008682815181106106d557fe5b6020026020010151905060008683815181106106ed57fe5b60200260200101519050600086848151811061070557fe5b60200260200101519050600086858151811061071d57fe5b60200260200101519050600086868151811061073557fe5b60200260200101519050604051806080016040528085815260200184815260200183815260200160011515815250600160008760ff1660ff168152602001908152602001600020600083815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff021916908315150217905550905050505050505080806001019150506106be565b505050505050565b60008090505b845181101561086b5782818151811061080357fe5b602002602001015160008087848151811061081a57fe5b602002602001015160ff1660ff168152602001908152602001600020600084848151811061084457fe5b602002602001015181526020019081526020016000208190555080806001019150506107ee565b5050505050565b6001602052816000526040600020602052806000526040600020600091509150508060000154908060010154908060020154908060030160009054906101000a900460ff1690508456fea265627a7a72305820dcaabec973ecc93f6248532e606385386b8ed5ae14d4d057c600e062b7e98f8264736f6c63430005090032";

  public static final String FUNC_LOCKTIMEOUT = "lockTimeout";

  public static final String FUNC_UPDATETRANSFERDONE = "updateTransferDone";

  public static final String FUNC_UPDATELOCKDONE = "updateLockDone";

  public static final String FUNC_TRANSFERDONE = "transferDone";

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

  public RemoteCall<BigInteger> lockTimeout(BigInteger rollupId, byte[] hash) {
    final Function function =
        new Function(
            FUNC_LOCKTIMEOUT,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint8(rollupId),
                new org.web3j.abi.datatypes.generated.Bytes32(hash)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteCall<TransactionReceipt> updateTransferDone(
      List<BigInteger> rollupId_list,
      List<byte[]> from_list,
      List<byte[]> to_list,
      List<BigInteger> amount_list,
      List<byte[]> hash_list) {
    final Function function =
        new Function(
            FUNC_UPDATETRANSFERDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                    org.web3j.abi.Utils.typeMap(
                        rollupId_list, org.web3j.abi.datatypes.generated.Uint8.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        from_list, org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        to_list, org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                    org.web3j.abi.Utils.typeMap(
                        amount_list, org.web3j.abi.datatypes.generated.Uint256.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        hash_list, org.web3j.abi.datatypes.generated.Bytes32.class))),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<TransactionReceipt> updateLockDone(
      List<BigInteger> sourceRollupId_list,
      List<BigInteger> targetRollupId_list,
      List<BigInteger> timeout_list,
      List<byte[]> hash_list) {
    final Function function =
        new Function(
            FUNC_UPDATELOCKDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                    org.web3j.abi.Utils.typeMap(
                        sourceRollupId_list, org.web3j.abi.datatypes.generated.Uint8.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                    org.web3j.abi.Utils.typeMap(
                        targetRollupId_list, org.web3j.abi.datatypes.generated.Uint8.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                    org.web3j.abi.Utils.typeMap(
                        timeout_list, org.web3j.abi.datatypes.generated.Uint256.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        hash_list, org.web3j.abi.datatypes.generated.Bytes32.class))),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<Tuple4<byte[], byte[], BigInteger, Boolean>> transferDone(
      BigInteger param0, byte[] param1) {
    final Function function =
        new Function(
            FUNC_TRANSFERDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint8(param0),
                new org.web3j.abi.datatypes.generated.Bytes32(param1)),
            Arrays.<TypeReference<?>>asList(
                new TypeReference<Bytes32>() {},
                new TypeReference<Bytes32>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<Bool>() {}));
    return new RemoteCall<Tuple4<byte[], byte[], BigInteger, Boolean>>(
        new Callable<Tuple4<byte[], byte[], BigInteger, Boolean>>() {
          @Override
          public Tuple4<byte[], byte[], BigInteger, Boolean> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple4<byte[], byte[], BigInteger, Boolean>(
                (byte[]) results.get(0).getValue(),
                (byte[]) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue(),
                (Boolean) results.get(3).getValue());
          }
        });
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
