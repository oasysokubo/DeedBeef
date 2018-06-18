// Oasys Okubo
// ookubo
// 12B
// 2/20/18
// QueueEmptyException.java
// Returns exception with the specified detail message.

@SuppressWarnings("serial")
public class QueueEmptyException extends RuntimeException {
	QueueEmptyException(String s) {
		super(s);
	}

}