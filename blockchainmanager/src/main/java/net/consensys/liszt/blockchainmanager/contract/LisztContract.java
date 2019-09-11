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
      "608060405234801561001057600080fd5b50610813806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063054f8e5a146100515780635fd3bf9314610499578063ddd9965d146104e8578063f058bf621461052d575b600080fd5b610497600480360361012081101561006857600080fd5b810190808035906020019064010000000081111561008557600080fd5b82018360208201111561009757600080fd5b803590602001918460208302840111640100000000831117156100b957600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561011957600080fd5b82018360208201111561012b57600080fd5b8035906020019184602083028401116401000000008311171561014d57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156101ad57600080fd5b8201836020820111156101bf57600080fd5b803590602001918460208302840111640100000000831117156101e157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561024157600080fd5b82018360208201111561025357600080fd5b8035906020019184602083028401116401000000008311171561027557600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156102d557600080fd5b8201836020820111156102e757600080fd5b8035906020019184602083028401116401000000008311171561030957600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561036957600080fd5b82018360208201111561037b57600080fd5b8035906020019184602083028401116401000000008311171561039d57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103fd57600080fd5b82018360208201111561040f57600080fd5b8035906020019184602083028401116401000000008311171561043157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290803560ff16906020019092919080359060200190929190505050610595565b005b6104d2600480360360408110156104af57600080fd5b81019080803560ff16906020019092919080359060200190929190505050610748565b6040518082815260200191505060405180910390f35b610517600480360360208110156104fe57600080fd5b81019080803560ff16906020019092919050505061077c565b6040518082815260200191505060405180910390f35b6105666004803603604081101561054357600080fd5b81019080803560ff16906020019092919080359060200190929190505050610794565b604051808581526020018481526020018381526020018215151515815260200194505050505060405180910390f35b60008751905060008090505b8181101561071d5760008882815181106105b757fe5b602002602001015160ff169050808560ff1614610623578682815181106105da57fe5b60200260200101516000808760ff1660ff16815260200190815260200160002060008a858151811061060857fe5b60200260200101518152602001908152602001600020819055505b600086838151811061063157fe5b602002602001015190506000801b811461070e5760405180608001604052808e858151811061065c57fe5b602002602001015181526020018d858151811061067557fe5b602002602001015181526020018c858151811061068e57fe5b6020026020010151815260200160011515815250600160008860ff1660ff168152602001908152602001600020600083815260200190815260200160002060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083151502179055509050505b505080806001019150506105a1565b5081600260008560ff1660ff1681526020019081526020016000208190555050505050505050505050565b60008060008460ff1660ff168152602001908152602001600020600083815260200190815260200160002054905092915050565b60026020528060005260406000206000915090505481565b6001602052816000526040600020602052806000526040600020600091509150508060000154908060010154908060020154908060030160009054906101000a900460ff1690508456fea265627a7a72305820b3b37b87e3a726067e8a8fdee6ecfe1f5abf833257f439bbc8307bc7d67e72b364736f6c63430005090032";

  public static final String FUNC_UPDATE = "update";

  public static final String FUNC_LOCKTIMEOUT = "lockTimeout";

  public static final String FUNC_LASTROOTHASH = "lastRootHash";

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

  public RemoteCall<TransactionReceipt> update(
      List<byte[]> from_list,
      List<byte[]> to_list,
      List<BigInteger> amount_list,
      List<BigInteger> targetRollupId_list,
      List<byte[]> hash_list,
      List<BigInteger> timeout_list,
      List<byte[]> pending_transfer_hash_list,
      BigInteger rollupId,
      byte[] rootHash) {
    final Function function =
        new Function(
            FUNC_UPDATE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        from_list, org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        to_list, org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                    org.web3j.abi.Utils.typeMap(
                        amount_list, org.web3j.abi.datatypes.generated.Uint256.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint8>(
                    org.web3j.abi.Utils.typeMap(
                        targetRollupId_list, org.web3j.abi.datatypes.generated.Uint8.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        hash_list, org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                    org.web3j.abi.Utils.typeMap(
                        timeout_list, org.web3j.abi.datatypes.generated.Uint256.class)),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                    org.web3j.abi.Utils.typeMap(
                        pending_transfer_hash_list,
                        org.web3j.abi.datatypes.generated.Bytes32.class)),
                new org.web3j.abi.datatypes.generated.Uint8(rollupId),
                new org.web3j.abi.datatypes.generated.Bytes32(rootHash)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
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

  public RemoteCall<byte[]> lastRootHash(BigInteger param0) {
    final Function function =
        new Function(
            FUNC_LASTROOTHASH,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(param0)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    return executeRemoteCallSingleValueReturn(function, byte[].class);
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
