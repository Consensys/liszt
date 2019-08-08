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
import org.web3j.abi.datatypes.Utf8String;
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
      "608060405234801561001057600080fd5b50610a6c806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063702bf07a14610051578063c58718da14610220578063d77ad665146103e0578063e11349861461057a575b600080fd5b61021e600480360360e081101561006757600080fd5b810190602081018135600160201b81111561008157600080fd5b82018360208201111561009357600080fd5b803590602001918460018302840111600160201b831117156100b457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561010657600080fd5b82018360208201111561011857600080fd5b803590602001918460018302840111600160201b8311171561013957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929584359560ff602087013581169660408101359091169550606081013594509192509060a081019060800135600160201b8111156101aa57600080fd5b8201836020820111156101bc57600080fd5b803590602001918460018302840111600160201b831117156101dd57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061063a945050505050565b005b61021e600480360360a081101561023657600080fd5b60ff8235169190810190604081016020820135600160201b81111561025a57600080fd5b82018360208201111561026c57600080fd5b803590602001918460018302840111600160201b8311171561028d57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156102df57600080fd5b8201836020820111156102f157600080fd5b803590602001918460018302840111600160201b8311171561031257600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092958435959094909350604081019250602001359050600160201b81111561036c57600080fd5b82018360208201111561037e57600080fd5b803590602001918460018302840111600160201b8311171561039f57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106cb945050505050565b61048e600480360360408110156103f657600080fd5b60ff8235169190810190604081016020820135600160201b81111561041a57600080fd5b82018360208201111561042c57600080fd5b803590602001918460018302840111600160201b8311171561044d57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506107be945050505050565b60408051908101839052811515606082015260808082528551908201528451819060208083019160a084019189019080838360005b838110156104db5781810151838201526020016104c3565b50505050905090810190601f1680156105085780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b8381101561053b578181015183820152602001610523565b50505050905090810190601f1680156105685780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6106286004803603604081101561059057600080fd5b60ff8235169190810190604081016020820135600160201b8111156105b457600080fd5b8201836020820111156105c657600080fd5b803590602001918460018302840111600160201b831117156105e757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610924945050505050565b60408051918252519081900360200190f35b8260ff168460ff16146106c257816000808660ff1660ff168152602001908152602001600020826040518082805190602001908083835b602083106106905780518252601f199092019160209182019101610671565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209290925550505b50505050505050565b604051806080016040528085815260200184815260200183815260200160011515815250600160008760ff1660ff168152602001908152602001600020826040518082805190602001908083835b602083106107385780518252601f199092019160209182019101610719565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451805191946107799450859350019061099c565b506020828101518051610792926001850192019061099c565b50604082015160028201556060909101516003909101805460ff19169115159190911790555050505050565b60016020818152600093845260409384902083518085018301805192815290830194830194909420935282548451600293821615610100026000190190911692909204601f81018290048202830182019094528382529192909183919083018282801561086c5780601f106108415761010080835404028352916020019161086c565b820191906000526020600020905b81548152906001019060200180831161084f57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561090a5780601f106108df5761010080835404028352916020019161090a565b820191906000526020600020905b8154815290600101906020018083116108ed57829003601f168201915b50505050600283015460039093015491929160ff16905084565b60ff8216600090815260208181526040808320905184519192859282918401908083835b602083106109675780518252601f199092019160209182019101610948565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205495945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109dd57805160ff1916838001178555610a0a565b82800160010185558215610a0a579182015b82811115610a0a5782518255916020019190600101906109ef565b50610a16929150610a1a565b5090565b610a3491905b80821115610a165760008155600101610a20565b9056fea265627a7a72305820df45b6f108f2020c2937561e278571c883b27e6c1ca2a6807645a2b1d4d2cfd464736f6c63430005090032";

  public static final String FUNC_UPDATELOCKDONE = "updateLockDone";

  public static final String FUNC_UPDATETRANSFERDONE = "updateTransferDone";

  public static final String FUNC_TRANSFERDONE = "transferDone";

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

  public RemoteCall<TransactionReceipt> updateTransferDone(
      BigInteger rollupId, String from, String to, BigInteger amount, String hash) {
    final Function function =
        new Function(
            FUNC_UPDATETRANSFERDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint8(rollupId),
                new org.web3j.abi.datatypes.Utf8String(from),
                new org.web3j.abi.datatypes.Utf8String(to),
                new org.web3j.abi.datatypes.generated.Uint256(amount),
                new org.web3j.abi.datatypes.Utf8String(hash)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteCall<Tuple4<String, String, BigInteger, Boolean>> transferDone(
      BigInteger param0, String param1) {
    final Function function =
        new Function(
            FUNC_TRANSFERDONE,
            Arrays.<Type>asList(
                new org.web3j.abi.datatypes.generated.Uint8(param0),
                new org.web3j.abi.datatypes.Utf8String(param1)),
            Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {},
                new TypeReference<Utf8String>() {},
                new TypeReference<Uint256>() {},
                new TypeReference<Bool>() {}));
    return new RemoteCall<Tuple4<String, String, BigInteger, Boolean>>(
        new Callable<Tuple4<String, String, BigInteger, Boolean>>() {
          @Override
          public Tuple4<String, String, BigInteger, Boolean> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple4<String, String, BigInteger, Boolean>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (BigInteger) results.get(2).getValue(),
                (Boolean) results.get(3).getValue());
          }
        });
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
