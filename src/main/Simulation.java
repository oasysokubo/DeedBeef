import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Simulation {

	public static Job getJob(Scanner in) {
		String[] s = in.nextLine().split(" ");
		int a = Integer.parseInt(s[0]);
		int d = Integer.parseInt(s[1]);
		return new Job(a, d);
	}

	public static void main(String[] args) throws IOException {

		// check command line argument
		if (args.length != 1) {
			System.err.println("Usage: Simulation <input_file>");
			System.exit(1);
		}

		// open files for reading and writing
		Scanner in = null;
		PrintWriter report = null;
		PrintWriter trace = null;
		Queue[] Processors = null;
		Queue BackUp = new Queue();
		Queue Storage = new Queue();

		// try to open input file to read
		try {
			in = new Scanner(new File(args[0]));
			report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
			trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
		} catch (IOException e) {
			throw new IOException("File not Found!");
		}

		// read in m jobs from input file
		int m = Integer.parseInt(in.nextLine());

		// start reading from input file
		for (int i = 0; i < m; i++) {
			Job job = getJob(in);
			BackUp.enqueue(job);
			Storage.enqueue(job);
		}

		// print first four lines of trace file
		trace.println("Trace file: " + args[0] + ".trc");
		trace.println(m + " Jobs:");
		trace.println(Storage);
		trace.println();

		// print first five lines of report file
		report.println("Report file: " + args[0] + ".rpt");
		report.println(m + " Jobs:");
		report.println(Storage);
		report.println();
		report.println("***********************************************************");

		// runs simulation from n processors for n=1 to n=m-1
		for (int n = 1; n < m; n++) {

			// via Trace file
			String noun = (n == 1) ? " processor:" : " processors:";
			trace.println("*****************************");
			trace.println(n + noun);
			trace.println("*****************************");

			// declare time variables
			int time = 0;
			int z = 0;
			int compare = 0;
			int index = 0;
			int finishTime = 0;

			// declare array of n processors
			Processors = new Queue[n];

			// initialize array of n processors
			for (int i = 0; i < n; i++) {
				Processors[i] = new Queue();
			}

			// check if Storage is empty
			if (Storage.isEmpty()) {
				for (int i = 0; i < m; i++) {
					Job J = (Job) BackUp.dequeue();
					J.resetFinishTime();
					BackUp.enqueue(J);
					Storage.enqueue(J);
				}
			}

			// via Trace file
			trace.println("time=" + time);
			trace.println("0: " + Storage.toString());
			for (int i = 1; i <= Processors.length; i++) {
				trace.println(i + ": " + Processors[i - 1].toString());
			}
			trace.println();

			// while more jobs are available
			while (z < m) {
				if (compare < m) {

					finishTime = findFinish(Processors);
					index = (((Job) Storage.peek()).getArrival());

					if (finishTime < index && finishTime != Job.UNDEF) {
						z += completeFinishedJobs(trace, Processors, Storage, finishTime);

						time = finishTime;

					}

					else if (finishTime > index || finishTime == Job.UNDEF) {
						time = index;
						compare += ProcessQueues(trace, Processors, Storage, index);

					} else if (finishTime == index) {
						z += completeFinishedJobs(trace, Processors, Storage, finishTime);
						compare += ProcessQueues(trace, Processors, Storage, index);
						time = finishTime;
					}
				}
				else {
					finishTime = findFinish(Processors);
					z += completeFinishedJobs(trace, Processors, Storage, finishTime);
					// update time
					time = finishTime;

				}
				trace.println("time=" + time);
				trace.println("0: " + Storage.toString());
				for (int i = 1; i <= Processors.length; i++) {
					trace.println(i + ": " + Processors[i - 1].toString());
				}
				trace.println();
			}

			// Report calculation initialization
			int totalWait = 0;
			int maxWait = 0;
			double averageWait = 0;

			// for all jobs in
			for (int i = 0; i < m; i++) {

				// dequeue job
				Job J = (Job) Storage.dequeue();

				// add wait time of job to total wait time
				totalWait += J.getWaitTime();

				// if wait time of current job is greater that maxWait, update maxWait
				if (J.getWaitTime() > maxWait) {
					maxWait = J.getWaitTime();
				}
			}

			// calculate average
			averageWait = (double) totalWait / (double) m;

			// Decimal Format for output
			DecimalFormat df = new DecimalFormat("#0.00");

			// via Report file
			String noun1 = (n == 1) ? " processor: " : " processors: ";
			report.println(n + noun1 + "totalWait=" + totalWait + ", maxWait=" + maxWait + ", averageWait="
					+ df.format(averageWait));
		}

		// close input and output files
		in.close();
		report.close();
		trace.close();
	}

	// findFinish() finds the latest completed finish time
	public static int findFinish(Queue[] Processors) {

		int completed = -1;

		for (int i = 0; i < Processors.length; i++) {
			if (!Processors[i].isEmpty()) {
				Job job = (Job) Processors[i].peek();
				if (completed == Job.UNDEF) {
					completed = job.getFinish();
				}
				else if (completed > job.getFinish()) {
					completed = job.getFinish();
				}
			}
		}

		return completed;
	}

	public static int completeFinishedJobs(PrintWriter trace, Queue[] Processors, Queue Storage, int nextFinishTime) {

		int m = 0;
		for (int i = 0; i < Processors.length; i++) {
			if (!Processors[i].isEmpty()) {
				if (((Job) Processors[i].peek()).getFinish() == nextFinishTime) {
					Job J = (Job) Processors[i].dequeue();
					m++;
					Storage.enqueue(J);
					if (!Processors[i].isEmpty()) {
						((Job) Processors[i].peek()).computeFinishTime(nextFinishTime);
					}
				}
			}
		}

		return m;
	}

	// ProccessQueues() puts the jobs into Processor
	public static int ProcessQueues(PrintWriter trace, Queue[] Processors, Queue Storage, int time) {
		int m = 0;
		while (true) {
			if (Storage.isEmpty() || ((Job) Storage.peek()).getArrival() != time) {
				break;
			}
			Job job = (Job) Storage.dequeue();
			int index = findIndex(Processors);
			if (Processors[index].isEmpty()) {
				job.computeFinishTime(time);
			}
			Processors[index].enqueue(job);

			m++;
		}
		return m;
	}

	// findIndex() finds the lowest index
	public static int findIndex(Queue[] Processors) {
		int index = -1;
		int length = -1;
		int preIndex = 0;
		boolean found = false;
		while (!found && preIndex < Processors.length) {

			if (Processors[preIndex].isEmpty()) {
				index = preIndex;
				found = true;
			} else {
				if (index == -1) {
					index = preIndex;
					length = Processors[preIndex].length();
				} else if (Processors[preIndex].length() < length) {
					index = preIndex;
					length = Processors[preIndex].length();
				}
			}
			preIndex++;
		}

		return index;
	}
}