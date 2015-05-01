package tests;

public class MemberInheritTest {

    public abstract static class InnerField {
        public InnerField(int fieldId, String fieldName) {
            this.fieldId = fieldId;
            this.fieldName = fieldName;
            this.defaultValue = null;
        }

        private final int fieldId;
        private final String fieldName;
        private final String defaultValue;
        private String fieldValue;

        public int getFieldId() { return fieldId; }
        public String getFieldName() { return fieldName; }
        public String getFieldValue() { return fieldValue; }
        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
        public String howDoIParse() { return "don't use this"; }
    }


    public static class SenderBic extends InnerField {
        public SenderBic() {
            super(1,"SENDER_BIC");
        }

        @Override
        public String howDoIParse() { return "sender->+->+::+"; }
    }

    public static class ReceiverBic extends InnerField {
        public ReceiverBic() {
            super(2,"RECEIVER_BIC");
        }

        @Override
        public String howDoIParse() { return "receiver$$8+=+"; }
    }

    public static class Seme extends InnerField {
        public Seme() {
            super(7,"SEME");
        }
    }

    public interface MessageFieldBehavior {
        public int getFieldId();
        public String getFieldName();
        public String getFieldValue();
        public void setFieldValue(String fieldValue);
        public String howDoIParse();

    }


    public static class FieldModel {
        public InnerField senderBic = new SenderBic();
        public InnerField receiverBic = new ReceiverBic();
        public InnerField seme = new Seme();
    }

    public static class FieldModelEnum {
        //public enum Columns implements MessageFieldBehavior { }

    }


    public static void main(String[] args) {
        FieldModel fm = new FieldModel();

        System.out.println("Plain old Field Model: Sender Bic Field ID is: ");
        System.out.println(fm.senderBic.getFieldId());
        System.out.println("-------------------");
        //System.out.println("Extended Field Model: Seme Field ID is: ");
        //System.out.println(ext.seme.getFieldId());


        System.out.println("Plain old Field Model: Receiver Bic behavior is: ");
        System.out.println("Field Id: " + fm.receiverBic.getFieldId());
        System.out.println("How do I parse: " + fm.receiverBic.howDoIParse());
        System.out.println("-------------------");
        System.out.println("Plain old Field Model: Seme behavior is: ");
        System.out.println("Field Id: " + fm.seme.getFieldId());
        System.out.println("How do I parse: " + fm.seme.howDoIParse());
        System.out.println("-------------------");
        //System.out.println("Extended Field Model: PSET behavior is: ");
        //System.out.println("Field Id: " + ext.pset.getFieldId());
        //System.out.println("How do I parse: " + ext.pset.howDoIParse());
    }

}
