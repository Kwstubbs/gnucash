import org.gnucash.numbers.FixedPointNumber;
import org.gnucash.read.GnucashAccount;
import org.gnucash.read.GnucashTransaction;
import org.gnucash.read.GnucashTransactionSplit;
import org.gnucash.read.impl.GnucashFileImpl;
import org.gnucash.write.GnucashWritableTransaction;
import org.gnucash.write.GnucashWritableTransactionSplit;
import org.gnucash.write.impl.GnucashFileWritingImpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Deniss Larka
 * on 19.Apr.2017
 */
public class GnuCashSandbox {

	public static void main(String[] args) throws IOException {

		GnuCashSandbox sandbox = new GnuCashSandbox();
		sandbox.process();
	}

	private void process() throws IOException {

		GnucashFileWritingImpl gnucashFile = new GnucashFileWritingImpl(new File("/tmp/aaa/test.gnucash"));
		Collection<GnucashAccount> accounts = gnucashFile.getAccounts();
		for (GnucashAccount account : accounts) {
			System.out.println(account.getQualifiedName());
		}



		GnucashWritableTransaction writableTransaction = gnucashFile.createWritableTransaction();
		writableTransaction.setDescription("check");
		writableTransaction.setCurrencyID("EUR");
		writableTransaction.setDateEntered(LocalDateTime.now());
		GnucashWritableTransactionSplit writingSplit = writableTransaction.createWritingSplit(gnucashFile.getAccountByName("Root Account::Income::Bonus"));
		writingSplit.setValue(new FixedPointNumber(100));
		writingSplit.setDescription("descr");

		Collection<? extends GnucashTransaction> transactions = gnucashFile.getTransactions();
		for (GnucashTransaction transaction : transactions) {
			System.out.println(transaction.getDatePosted());
			List<GnucashTransactionSplit> splits = transaction.getSplits();
			for (GnucashTransactionSplit split : splits) {
				System.out.println("\t"+split.getQuantity());
			}
		}

	}
}

