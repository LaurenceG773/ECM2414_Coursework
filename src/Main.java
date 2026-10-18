//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main()
{
    Path folderpath = Path.of("CardFiles");
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter number of players: ");
    var numPlayers = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Enter just filename: ");
    var filename = scanner.nextLine();

    Path filePath = folderpath.resolve(filename);

    var game = new CardGame(numPlayers, filePath);
    game.start();
}
