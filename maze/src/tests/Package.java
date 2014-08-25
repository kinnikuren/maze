package tests;

public class Package {
    private Object contents;
    private Label label;

    public Package() {
        contents = null;
    }

    public Package(Object o) {
        contents = o;
    }

    public Package(Object o, String from, String to, String owner, int packageCode) {
        contents = o;
        label = newLabel(from, to, owner, packageCode);
    }

    public Label newLabel(String from, String to, String owner, int packageCode) {
        System.out.println("creating new label");
        return new Label(from, to, owner, packageCode);
    }

    public Label label() { return label; }

    public void setLabel(Label l) {
        System.out.println("changing the package label");
        label = l;
    }

    public void printLabel() {
        label.printout();
    }

    public void printContents() {
        System.out.println("Contents: " + contents);
    }

    public class Label {
        private String from;
        private String to;
        private String owner;
        private int packageCode;

        public Label(String from, String to, String owner, int packageCode) {
            this.from = from;
            this.to = to;
            this.owner = owner;
            this.packageCode = packageCode;
        }

        public void printout() {
            System.out.println("From: " + this.from + " To: " + this.to
                    + " Owner: " + this.owner);
        }

        public Package getPackage() {
            return Package.this;
        }
    }
}
