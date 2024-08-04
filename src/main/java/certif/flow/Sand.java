package certif.flow;

public class Sand {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY,
        WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    public static void main(String[] args) {
        // switch expressions must be exhaustive
        // and yield or throw
        Day day = Day.WEDNESDAY;
        System.out.println(expressionSwitchLabel(day));
        System.out.println(expressionSwitchStatement(day));
        statementSwitchNonExhaustive(day);
        statementSwitchNull(null);
        statementSwitchPattern(new PetiteFille());
        System.out.println(expressionSwitchLabelPattern(new PetiteFille()));
        System.out.println(expressionSwitchLabelPatternWhen(new PetiteFille()));

        Mere m = new PetiteFille();
        if (m instanceof PetiteFille pf) {
            System.out.println("instance of PetiteFille " + pf);
            if (pf instanceof Fille f) {
                System.out.println("instance of Fille " + f);
            }
        }

        spread();
    }

    public static int expressionSwitchLabel(Day day) {
        return switch (day) {
            // switch labeled rule {
            case MONDAY, FRIDAY, SUNDAY -> {
                System.out.println(6);
                yield 6;
            }
            // }
            case WEDNESDAY -> {
                System.out.println(7);
                yield 7;
            }
            case TUESDAY -> 3;
            // cas null soit seul soit avec default
            case null, default -> throw new IllegalStateException("Invalid day: " + day);
        };
    }

    public static int expressionSwitchStatement(Day day) {
        // has fallthrough
        return switch (day) {
            // switch labeled statement group {
            case MONDAY:
            case null:
            case FRIDAY:
            case SUNDAY: {
                System.out.println(8);
                yield 8;
            }
            // }
            case WEDNESDAY: {
                System.out.println(9);
            }
            case TUESDAY:
                yield 10;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        };
    }

    public static void statementSwitchNonExhaustive(Day day) {
        switch (day) {
            case WEDNESDAY:
                System.out.println(2);
                System.out.println(3);
            case MONDAY:
            case SUNDAY: {
                System.out.println(1);
            }
        }
    }

    public static void statementSwitchNull(Day day) {
        switch (day) {
            case null:
            case FRIDAY:
                System.out.println("null");
            case WEDNESDAY:
            case TUESDAY:
            case THURSDAY:
                System.out.println(2);
                System.out.println(3);
            case MONDAY:
            case SATURDAY:
            case SUNDAY: {
                System.out.println(1);
            }
        }
    }

    static void statementSwitchPattern(Mere mere) {
        switch (mere) {
            case PetiteFille pf when pf.code < 5: System.out.println(pf.hashCode());break;// no fall-through to a pattern
            case Fille f: {
                System.out.println("fille " + f);
                break;
            }
            case null, default: { //only null can be combined with default
                System.out.println("not fille");
            }
        }
    }

    public static int expressionSwitchLabelPattern(Mere mere) {
        return switch (mere) {
            case null -> throw new RuntimeException("null");
            case PetiteFille pf -> pf.hashCode();
            default -> throw new IllegalStateException("Invalid mere: " + mere);
        };
    }

    public static int expressionSwitchLabelPatternWhen(Mere mere) {
        return switch (mere) {
            case PetiteFille pf when pf.code < 5 -> pf.hashCode();
            case PetiteFille pf when pf.code > 9 -> pf.hashCode();
            case Fille f -> f.hashCode();
            //case Mere m -> m.hashCode(); //interdit car tous doivent être accessibles
            default -> 100;
        };
    }

    public static void spread() {
        Object obj = new SpreadRecord("mine", 22);
        if (obj instanceof SpreadRecord(String nom, Integer numero)) {
            System.out.println(nom + " is " + numero);
            //nested records are supported too
        }
    }
}

class Mere {
    long code = (long) (Math.random() * 10);
    {
        System.out.println("code is " + code);
    }
}

class Fille extends Mere {

}

class PetiteFille extends Fille {

}

record SpreadRecord(String name, Integer num) {}