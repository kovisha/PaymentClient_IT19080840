package model;

public class PaymentModel {

	//private attribute declaration.
	
		private int PaymentID;
		private String paymentCode;
		private String paymentType;
		private String bank;
		private String paymentDate;
		private String paymentDescription;
		private String nameOnCard;
		private String cardNo;
		private String cvv;
		private int cardExpMonth;
		private int cardExpYear;
		private double buyerPayment;
		private double pledgedAmount;
		private int productID;
		private int consumerID;
		private int conceptID;
		
		//default constructor.
		public PaymentModel() {
			super();
		}

		//Class getter methods.
		public int getPaymentID() {
			return PaymentID;
		}

		public String getPaymentCode() {
			return paymentCode;
		}

		public String getPaymentType() {
			return paymentType;
		}

		public String getBank() {
			return bank;
		}

		public String getPaymentDate() {
			return paymentDate;
		}

		public String getPaymentDescription() {
			return paymentDescription;
		}

		public String getNameOnCard() {
			return nameOnCard;
		}

		public String getCardNo() {
			return cardNo;
		}

		public String getCvv() {
			return cvv;
		}

		public int getCardExpMonth() {
			return cardExpMonth;
		}

		public int getCardExpYear() {
			return cardExpYear;
		}

		public double getBuyerPayment() {
			return buyerPayment;
		}

		public double getPledgedAmount() {
			return pledgedAmount;
		}

		public int getProductID() {
			return productID;
		}

		public int getConsumerID() {
			return consumerID;
		}

		public int getConceptID() {
			return conceptID;
		}

		
		//Class setter methods.
		
		public void setPaymentID(int paymentID) {
			PaymentID = paymentID;
		}

		public void setPaymentNo(String paymentCode) {
			this.paymentCode = paymentCode;
		}

		public void setPaymentType(String paymentType) {
			this.paymentType = paymentType;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public void setPaymentDate(String paymentDate) {
			this.paymentDate = paymentDate;
		}

		public void setPaymentDescription(String paymentDescription) {
			this.paymentDescription = paymentDescription;
		}

		public void setNameOnCard(String nameOnCard) {
			this.nameOnCard = nameOnCard;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public void setCvv(String cvv) {
			this.cvv = cvv;
		}

		public void setCardExpMonth(int cardExpMonth) {
			this.cardExpMonth = cardExpMonth;
		}

		public void setCardExpYear(int cardExpYear) {
			this.cardExpYear = cardExpYear;
		}

		public void setBuyerPayment(double buyerPayment) {
			this.buyerPayment = buyerPayment;
		}

		public void setPledgedAmount(double pledgedAmount) {
			this.pledgedAmount = pledgedAmount;
		}

		public void setProductID(int productID) {
			this.productID = productID;
		}

		public void setConsumerID(int consumerID) {
			this.consumerID = consumerID;
		}

		public void setConceptID(int conceptID) {
			this.conceptID = conceptID;
		}
		
		
		//Method to validate digits in User card.
		public int validateCardNumber(String cardNumber) {
			
			int count = 0;
			
			//Counts each character except space .   
	        for(int i = 0; i < cardNumber.length(); i++) {    
	            if(cardNumber.charAt(i) != ' ')    
	                count++;    
	        }   
	        
	        return count;
		}
		
		//Method to validate cvv of user card.
		public int cvvValidator(String cardNumber , String cvv) {
			
			String extractedPortion = cardNumber.substring(13);
			
			if(extractedPortion.equals(cvv)) {
				return 1;
			}
			else {
				return 0 ;
			}
		}
		
}
