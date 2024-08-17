package certif.oop;

public record PRaccess(Ieah h) {
    private static class Ieah {
        String getIt() {return "it";}
    }

    public static void main(String[] args) {
        PRaccess pRaccess = new PRaccess(new Ieah());
        System.out.println(pRaccess.h().getIt());
    }
}
