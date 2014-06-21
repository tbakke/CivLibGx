/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygdx.CivClick.core;

import mygdx.CivClick.CivClicker;
import mygdx.CivClick.util.Console;
import mygdx.CivClick.util.Shorthand;
import mygdx.CivClick.util.Devmode;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Whiplash
 */
public class CivUI extends javax.swing.JFrame implements Updateable {
    
    private int modifieramount = 1, modifierindexpoint = 0, inputhistoryindexpoint = 0;
    private int[] modifiernumbers = new int[]{1, 10, 100, 1000};
    private List<String> inputhistory = new ArrayList<>();
    private boolean buy = true, inputkeypressed = false;
    private Game game;
    private TownshipTitle.Title currentTitle;

    /**
     * Creates new form CivUI
     *
     * @param game
     * @param devmode
     */
    public CivUI(Game game, boolean devmode) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                } else {
                    javax.swing.UIManager.getSystemLookAndFeelClassName();
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {

        }

        this.game = game;
        initComponents();
        initButtons();
        initConsoleField();
        ConsoleInputField.setEnabled(devmode);
        ConsoleInputField.setVisible(devmode);
        initBuildingdisplay(game.bm);
        getContentPane().setBackground(new java.awt.Color(228, 236, 248)); //CEDCF1
        FoodButton.setSelected(true);
        ResourceButton.setText("Gather Food!");
        ResourceButton.setBackground(new java.awt.Color(250, 209, 26));
        inputhistory.add("");
        currentTitle = game.pm.title.getTitle();
        TownshipLabel.setText(currentTitle.toString());
    }

    private void initButtons() {
        Console.println("Initializing button state...", Console.Type.s);
        ApothecaryContainer.setVisible(false);
        BlacksmithContainer.setVisible(false);
        TannerContainer.setVisible(false);
    }

    private void initConsoleField() {
        ConsoleOutputWindow.setText("");
        Console.setOut(ConsoleOutputWindow);

    }

    private void displayResources(ResourceManager rm) {
        FoodAmountLabel.setText(rm.food.displayAmount() + rm.food.displayMaxAmount());
        WoodAmountLabel.setText(rm.wood.displayAmount() + rm.wood.displayMaxAmount());
        StoneAmountLabel.setText(rm.stone.displayAmount() + rm.stone.displayMaxAmount());
        LandLabel.setText(rm.land.getName() + ": " + rm.land.displayAmount() + rm.land.displayMaxAmount());
        HerbsAmountLabel.setText(rm.herbs.getName() + ": " + rm.herbs.displayAmount());
        LeatherAmountLabel.setText(rm.leather.getName() + ": " + rm.leather.displayAmount());
        SkinsAmountLabel.setText(rm.skins.getName() + ": " + rm.skins.displayAmount());
        OreAmountLabel.setText(rm.ore.getName() + ": " + rm.ore.displayAmount());
        MetalAmountLabel.setText(rm.metal.getName() + ": " + rm.metal.displayAmount());
        SharedResourceAmountLabel.setText(Shorthand.parse(rm.GetSharedAmount(game.bm.storagestockpile)) + " Max: " + Shorthand.parse(game.bm.storagestockpile.getAmount() * game.bm.storagestockpile.getCapacity()));
        SharedResourceStorageBar.setMaximum((int) (game.bm.storagestockpile.getAmount() * game.bm.storagestockpile.getCapacity()));
        SharedResourceStorageBar.setValue((int) rm.GetSharedAmount(game.bm.storagestockpile));
        FoodBar.setMaximum((int) rm.food.getMaxAmount());
        FoodBar.setValue((int) rm.food.getAmount());
        WoodBar.setMaximum((int) rm.wood.getMaxAmount());
        WoodBar.setValue((int) rm.wood.getAmount());
        StoneBar.setMaximum((int) rm.stone.getMaxAmount());
        StoneBar.setValue((int) rm.stone.getAmount());
        LandBar.setMaximum((int) rm.land.getMaxAmount());
        LandBar.setValue((int) rm.land.getAmount());
    }

    private void initBuildingdisplay(BuildingManager bm) {
        Console.println("Initialzing building display...", Console.Type.s);
        FarmButton.setText(bm.farm.getName() + ": " + bm.farm.displayAmount());
        StorageButton.setText(bm.storagestockpile.getName() + ": " + bm.storagestockpile.displayAmount());
        BarnButton.setText(bm.barn.getName() + ": " + bm.barn.displayAmount());
        ApothecaryButton.setText(bm.apothecary.getName() + ": " + bm.apothecary.displayAmount());
        SchoolButton.setText(bm.school.getName() + ": " + bm.school.displayAmount());
        GreenhouseButton.setText(bm.greenhouse.getName() + ": " + bm.greenhouse.displayAmount());
        ChurchButton.setText(bm.church.getName() + ": " + bm.church.displayAmount());
        HuntingLodgeButton.setText(bm.huntinglodge.getName() + ": " + bm.huntinglodge.displayAmount());
        GraveyardButton.setText(bm.graveyard.getName() + ": " + bm.graveyard.displayAmount());
        SmitheyButton.setText(bm.smithey.getName() + ": " + bm.smithey.displayAmount());
        TanneryButton.setText(bm.tannery.getName() + ": " + bm.tannery.displayAmount());
        HutCottageCabinHouseButton.setText(bm.hut.getName() + ": " + bm.hut.displayAmount());
        StoneStockpileButton.setText(bm.stonestockpile.getName() + ": " + bm.stonestockpile.displayAmount());
        LumberyardButton.setText(bm.lumberyard.getName() + ": " + bm.lumberyard.displayAmount());
        BarracksButton.setText(bm.barracks.getName() + ": " + bm.barracks.displayAmount());
        HutCottageCabinHouseCostLabel.setText(bm.hut.displayCost());
        TanneryCostLabel.setText(bm.tannery.displayCost());
        SmitheyCostLabel.setText(bm.smithey.displayCost());
        FarmCostLabel.setText(bm.farm.displayCost());
        GraveyardCostLabel.setText(bm.graveyard.displayCost());
        HuntingLodgeCostLabel.setText(bm.huntinglodge.displayCost());
        ChurchCostLabel.setText(bm.church.displayCost());
        GreenhouseCostLabel.setText(bm.greenhouse.displayCost());
        SchoolCostLabel.setText(bm.school.displayCost());
        ApothecaryCostLabel.setText(bm.apothecary.displayCost());
        BarracksCostLabel.setText(bm.barracks.displayCost());
        BarnCostLabel.setText(bm.barn.displayCost());
        StorageCostLabel.setText(bm.storagestockpile.displayCost());
        StoneStockpileCostLabel.setText(bm.stonestockpile.displayCost());
        LumberyardCostLabel.setText(bm.lumberyard.displayCost());
        TradingPostCostLabel.setText(bm.tradingpost.displayCost());
    }

    private void displayPopulation(PopulationManager pm) {
        PopulationLabel.setText("Population: " + Shorthand.parse(pm.getPopulationAmount()) + " Max: " + Shorthand.parse(pm.getMaxPopulationAmount()));
        UnemployedAmountLabel.setText("Unemployed: " + pm.unemployed.displayAmount());
        WorkerCostLabel.setText(Shorthand.parse(pm.getNextWorkerCost()));
        FarmerAmountLabel.setText(pm.farmer.displayAmount());
        ForesterAmountLabel.setText(pm.forester.displayAmount());
        MinerAmountLabel.setText(pm.miner.displayAmount());
        ApothecaryAmountLabel.setText(pm.apothecary.displayAmount());
        TannerAmountLabel.setText(pm.tanner.displayAmount());
        BlacksmithAmountLabel.setText(pm.blacksmith.displayAmount());
        PopulationBar.setMaximum((int) pm.getMaxPopulationAmount());
        PopulationBar.setValue((int) pm.getPopulationAmount());
        if (currentTitle != pm.title.getTitle()) {
            currentTitle = pm.title.getTitle();
            TownshipLabel.setText(currentTitle.toString());
        }
    }

    @Override
    public void TDUpdate(Game game) {

    }

    @Override
    public void TIDUpdate(Game game) {
        displayResources(game.rm);
        displayPopulation(game.pm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainResourceButtonGroup = new javax.swing.ButtonGroup();
        TabPanel = new javax.swing.JTabbedPane();
        ResourcesPanel = new javax.swing.JPanel();
        SharedResourceStorageLabel = new javax.swing.JLabel();
        SharedResourceStorageBar = new javax.swing.JProgressBar();
        MetalAmountLabel = new javax.swing.JLabel();
        SkinsAmountLabel = new javax.swing.JLabel();
        SharedResourceAmountLabel = new javax.swing.JLabel();
        HerbsAmountLabel = new javax.swing.JLabel();
        OreAmountLabel = new javax.swing.JLabel();
        LeatherAmountLabel = new javax.swing.JLabel();
        UpgradesPanel = new javax.swing.JPanel();
        ExpansionPanel = new javax.swing.JPanel();
        BarracksButton = new javax.swing.JButton();
        BarracksCostLabel = new javax.swing.JLabel();
        ApothecaryContainer1 = new javax.swing.JPanel();
        SoldierRemoveButton = new javax.swing.JButton();
        SoldierLabel = new javax.swing.JLabel();
        SoldierAddButton = new javax.swing.JButton();
        SoldierAmountLabel = new javax.swing.JLabel();
        ReligionPanel = new javax.swing.JPanel();
        TradingPanel = new javax.swing.JPanel();
        TradingPostButton = new javax.swing.JButton();
        TradingPostCostLabel = new javax.swing.JLabel();
        AchievementsPanel = new javax.swing.JPanel();
        MainPanel = new javax.swing.JPanel();
        JobPanel = new javax.swing.JPanel();
        FarmerContainer = new javax.swing.JPanel();
        FarmerRemoveButton = new javax.swing.JButton();
        FarmerLabel = new javax.swing.JLabel();
        FarmerAddButton = new javax.swing.JButton();
        FarmerAmountLabel = new javax.swing.JLabel();
        ForesterContainer = new javax.swing.JPanel();
        ForesterRemoveButton = new javax.swing.JButton();
        ForesterLabel = new javax.swing.JLabel();
        ForesterAddButton = new javax.swing.JButton();
        ForesterAmountLabel = new javax.swing.JLabel();
        MinerContainer = new javax.swing.JPanel();
        MinerRemoveButton = new javax.swing.JButton();
        MinerLabel = new javax.swing.JLabel();
        MinerAddButton = new javax.swing.JButton();
        MinerAmountLabel = new javax.swing.JLabel();
        BlacksmithContainer = new javax.swing.JPanel();
        BlacksmithRemoveButton = new javax.swing.JButton();
        BlacksmithLabel = new javax.swing.JLabel();
        BlacksmithAddButton = new javax.swing.JButton();
        BlacksmithAmountLabel = new javax.swing.JLabel();
        TannerContainer = new javax.swing.JPanel();
        TannerRemoveButton = new javax.swing.JButton();
        TannerLabel = new javax.swing.JLabel();
        TannerAddButton = new javax.swing.JButton();
        TannerAmountLabel = new javax.swing.JLabel();
        ApothecaryContainer = new javax.swing.JPanel();
        ApothecaryRemoveButton = new javax.swing.JButton();
        ApothecaryLabel = new javax.swing.JLabel();
        ApothecaryAddButton = new javax.swing.JButton();
        ApothecaryAmountLabel = new javax.swing.JLabel();
        LandLabel = new javax.swing.JLabel();
        LandBar = new javax.swing.JProgressBar();
        PopulationLabel = new javax.swing.JLabel();
        PopulationBar = new javax.swing.JProgressBar();
        TownshipLabel = new javax.swing.JLabel();
        ResourceButton = new javax.swing.JButton();
        AddWorkerButton = new javax.swing.JButton();
        UnemployedAmountLabel = new javax.swing.JLabel();
        WorkerCostLabel = new javax.swing.JLabel();
        ConsoleOutputWindowPanel = new javax.swing.JScrollPane();
        ConsoleOutputWindow = new javax.swing.JTextArea();
        ClickResourcePanel = new javax.swing.JPanel();
        FoodButton = new javax.swing.JRadioButton();
        WoodButton = new javax.swing.JRadioButton();
        StoneButton = new javax.swing.JRadioButton();
        FoodBar = new javax.swing.JProgressBar();
        WoodBar = new javax.swing.JProgressBar();
        StoneBar = new javax.swing.JProgressBar();
        FoodAmountLabel = new javax.swing.JLabel();
        WoodAmountLabel = new javax.swing.JLabel();
        StoneAmountLabel = new javax.swing.JLabel();
        ConsoleInputField = new javax.swing.JTextField();
        OptionsPanel = new javax.swing.JPanel();
        SaveButton = new javax.swing.JButton();
        LoadButton = new javax.swing.JButton();
        RestartButton = new javax.swing.JButton();
        BuySellButton = new javax.swing.JButton();
        ModifierLabel = new javax.swing.JLabel();
        ModifierDecrementButton = new javax.swing.JButton();
        ModifierIncrementButton = new javax.swing.JButton();
        ModifierNumberLabel = new javax.swing.JLabel();
        BuildingPanel = new javax.swing.JPanel();
        HutCottageCabinHouseButton = new javax.swing.JButton();
        FarmButton = new javax.swing.JButton();
        SmitheyButton = new javax.swing.JButton();
        TanneryButton = new javax.swing.JButton();
        GraveyardButton = new javax.swing.JButton();
        ChurchButton = new javax.swing.JButton();
        ApothecaryButton = new javax.swing.JButton();
        SchoolButton = new javax.swing.JButton();
        HuntingLodgeButton = new javax.swing.JButton();
        GreenhouseButton = new javax.swing.JButton();
        BarnButton = new javax.swing.JButton();
        StorageButton = new javax.swing.JButton();
        StoneStockpileButton = new javax.swing.JButton();
        LumberyardButton = new javax.swing.JButton();
        HutCottageCabinHouseCostLabel = new javax.swing.JLabel();
        FarmCostLabel = new javax.swing.JLabel();
        SmitheyCostLabel = new javax.swing.JLabel();
        TanneryCostLabel = new javax.swing.JLabel();
        GraveyardCostLabel = new javax.swing.JLabel();
        ChurchCostLabel = new javax.swing.JLabel();
        ApothecaryCostLabel = new javax.swing.JLabel();
        SchoolCostLabel = new javax.swing.JLabel();
        HuntingLodgeCostLabel = new javax.swing.JLabel();
        GreenhouseCostLabel = new javax.swing.JLabel();
        BarnCostLabel = new javax.swing.JLabel();
        StorageCostLabel = new javax.swing.JLabel();
        LumberyardCostLabel = new javax.swing.JLabel();
        StoneStockpileCostLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(640, 360));
        setResizable(false);

        TabPanel.setBackground(new java.awt.Color(255, 247, 214));

        ResourcesPanel.setBackground(new java.awt.Color(206, 220, 241));
        ResourcesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SharedResourceStorageLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        SharedResourceStorageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SharedResourceStorageLabel.setText("Shared Resource Storage");
        ResourcesPanel.add(SharedResourceStorageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 680, 30));

        SharedResourceStorageBar.setForeground(new java.awt.Color(151, 151, 151));
        ResourcesPanel.add(SharedResourceStorageBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 180, -1));

        MetalAmountLabel.setText("Metal: Amount");
        ResourcesPanel.add(MetalAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        SkinsAmountLabel.setText("Skins: Amount");
        ResourcesPanel.add(SkinsAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, -1, -1));

        SharedResourceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SharedResourceAmountLabel.setText("Amount");
        ResourcesPanel.add(SharedResourceAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 180, -1));

        HerbsAmountLabel.setText("Herbs: Amount");
        ResourcesPanel.add(HerbsAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        OreAmountLabel.setText("Ore: Amount");
        ResourcesPanel.add(OreAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        LeatherAmountLabel.setText("Leather: Amount");
        ResourcesPanel.add(LeatherAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, -1));

        TabPanel.addTab("Resources", ResourcesPanel);

        UpgradesPanel.setBackground(new java.awt.Color(206, 220, 241));

        javax.swing.GroupLayout UpgradesPanelLayout = new javax.swing.GroupLayout(UpgradesPanel);
        UpgradesPanel.setLayout(UpgradesPanelLayout);
        UpgradesPanelLayout.setHorizontalGroup(
            UpgradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        UpgradesPanelLayout.setVerticalGroup(
            UpgradesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );

        TabPanel.addTab("Upgrades", UpgradesPanel);

        ExpansionPanel.setBackground(new java.awt.Color(206, 220, 241));
        ExpansionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarracksButton.setBackground(new java.awt.Color(255, 247, 214));
        BarracksButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        BarracksButton.setText("Barracks: Amount");
        BarracksButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BarracksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarracksButtonActionPerformed(evt);
            }
        });
        ExpansionPanel.add(BarracksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 30));

        BarracksCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        BarracksCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");
        ExpansionPanel.add(BarracksCostLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        ApothecaryContainer1.setFocusable(false);
        ApothecaryContainer1.setOpaque(false);
        ApothecaryContainer1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SoldierRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        SoldierRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        SoldierRemoveButton.setText("<");
        SoldierRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SoldierRemoveButton.setFocusable(false);
        SoldierRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SoldierRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        SoldierRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        SoldierRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        SoldierRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        SoldierRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoldierRemoveButtonActionPerformed(evt);
            }
        });
        ApothecaryContainer1.add(SoldierRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        SoldierLabel.setBackground(new java.awt.Color(156, 163, 170));
        SoldierLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SoldierLabel.setText("Soldier");
        SoldierLabel.setFocusable(false);
        SoldierLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        SoldierLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        ApothecaryContainer1.add(SoldierLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        SoldierAddButton.setBackground(new java.awt.Color(255, 247, 214));
        SoldierAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        SoldierAddButton.setText(">");
        SoldierAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SoldierAddButton.setFocusable(false);
        SoldierAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SoldierAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        SoldierAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        SoldierAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        SoldierAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        SoldierAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoldierAddButtonActionPerformed(evt);
            }
        });
        ApothecaryContainer1.add(SoldierAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        SoldierAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        SoldierAmountLabel.setText("Amount");
        SoldierAmountLabel.setFocusable(false);
        ApothecaryContainer1.add(SoldierAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        ExpansionPanel.add(ApothecaryContainer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        TabPanel.addTab("Expansion", ExpansionPanel);

        ReligionPanel.setBackground(new java.awt.Color(206, 220, 241));

        javax.swing.GroupLayout ReligionPanelLayout = new javax.swing.GroupLayout(ReligionPanel);
        ReligionPanel.setLayout(ReligionPanelLayout);
        ReligionPanelLayout.setHorizontalGroup(
            ReligionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        ReligionPanelLayout.setVerticalGroup(
            ReligionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );

        TabPanel.addTab("Religion", ReligionPanel);

        TradingPanel.setBackground(new java.awt.Color(206, 220, 241));
        TradingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TradingPostButton.setBackground(new java.awt.Color(255, 247, 214));
        TradingPostButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        TradingPostButton.setText("Unlock Trading Post");
        TradingPostButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        TradingPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TradingPostButtonActionPerformed(evt);
            }
        });
        TradingPanel.add(TradingPostButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 30));

        TradingPostCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        TradingPostCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");
        TradingPanel.add(TradingPostCostLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 30));

        TabPanel.addTab("Trading", TradingPanel);

        AchievementsPanel.setBackground(new java.awt.Color(206, 220, 241));

        javax.swing.GroupLayout AchievementsPanelLayout = new javax.swing.GroupLayout(AchievementsPanel);
        AchievementsPanel.setLayout(AchievementsPanelLayout);
        AchievementsPanelLayout.setHorizontalGroup(
            AchievementsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
        );
        AchievementsPanelLayout.setVerticalGroup(
            AchievementsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );

        TabPanel.addTab("Achievements", AchievementsPanel);

        MainPanel.setBackground(new java.awt.Color(161, 169, 181));
        MainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        JobPanel.setBackground(new java.awt.Color(228, 236, 248));
        JobPanel.setOpaque(false);

        FarmerContainer.setFocusable(false);
        FarmerContainer.setOpaque(false);
        FarmerContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FarmerRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        FarmerRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        FarmerRemoveButton.setText("<");
        FarmerRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FarmerRemoveButton.setFocusable(false);
        FarmerRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FarmerRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        FarmerRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        FarmerRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        FarmerRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        FarmerRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FarmerRemoveButtonActionPerformed(evt);
            }
        });
        FarmerContainer.add(FarmerRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        FarmerLabel.setBackground(new java.awt.Color(156, 163, 170));
        FarmerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FarmerLabel.setText("Farmer");
        FarmerLabel.setFocusable(false);
        FarmerLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        FarmerLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        FarmerContainer.add(FarmerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        FarmerAddButton.setBackground(new java.awt.Color(255, 247, 214));
        FarmerAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        FarmerAddButton.setText(">");
        FarmerAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FarmerAddButton.setFocusable(false);
        FarmerAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FarmerAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        FarmerAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        FarmerAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        FarmerAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        FarmerAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FarmerAddButtonActionPerformed(evt);
            }
        });
        FarmerContainer.add(FarmerAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        FarmerAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        FarmerAmountLabel.setText("Amount");
        FarmerAmountLabel.setFocusable(false);
        FarmerContainer.add(FarmerAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        ForesterContainer.setFocusable(false);
        ForesterContainer.setOpaque(false);
        ForesterContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ForesterRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        ForesterRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ForesterRemoveButton.setText("<");
        ForesterRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ForesterRemoveButton.setFocusable(false);
        ForesterRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ForesterRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ForesterRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ForesterRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ForesterRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ForesterRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForesterRemoveButtonActionPerformed(evt);
            }
        });
        ForesterContainer.add(ForesterRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        ForesterLabel.setBackground(new java.awt.Color(156, 163, 170));
        ForesterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ForesterLabel.setText("Forester");
        ForesterLabel.setFocusable(false);
        ForesterLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        ForesterLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        ForesterContainer.add(ForesterLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        ForesterAddButton.setBackground(new java.awt.Color(255, 247, 214));
        ForesterAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ForesterAddButton.setText(">");
        ForesterAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ForesterAddButton.setFocusable(false);
        ForesterAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ForesterAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ForesterAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ForesterAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ForesterAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ForesterAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForesterAddButtonActionPerformed(evt);
            }
        });
        ForesterContainer.add(ForesterAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        ForesterAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        ForesterAmountLabel.setText("Amount");
        ForesterAmountLabel.setFocusable(false);
        ForesterContainer.add(ForesterAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        MinerContainer.setFocusable(false);
        MinerContainer.setOpaque(false);
        MinerContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MinerRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        MinerRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        MinerRemoveButton.setText("<");
        MinerRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MinerRemoveButton.setFocusable(false);
        MinerRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MinerRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        MinerRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        MinerRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        MinerRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        MinerRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinerRemoveButtonActionPerformed(evt);
            }
        });
        MinerContainer.add(MinerRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        MinerLabel.setBackground(new java.awt.Color(156, 163, 170));
        MinerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MinerLabel.setText("Miner");
        MinerLabel.setFocusable(false);
        MinerLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        MinerLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        MinerContainer.add(MinerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        MinerAddButton.setBackground(new java.awt.Color(255, 247, 214));
        MinerAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        MinerAddButton.setText(">");
        MinerAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MinerAddButton.setFocusable(false);
        MinerAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MinerAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        MinerAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        MinerAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        MinerAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        MinerAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinerAddButtonActionPerformed(evt);
            }
        });
        MinerContainer.add(MinerAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        MinerAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        MinerAmountLabel.setText("Amount");
        MinerAmountLabel.setFocusable(false);
        MinerContainer.add(MinerAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        BlacksmithContainer.setFocusable(false);
        BlacksmithContainer.setOpaque(false);
        BlacksmithContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BlacksmithRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        BlacksmithRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        BlacksmithRemoveButton.setText("<");
        BlacksmithRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BlacksmithRemoveButton.setFocusable(false);
        BlacksmithRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BlacksmithRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BlacksmithRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        BlacksmithRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        BlacksmithRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        BlacksmithRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlacksmithRemoveButtonActionPerformed(evt);
            }
        });
        BlacksmithContainer.add(BlacksmithRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        BlacksmithLabel.setBackground(new java.awt.Color(156, 163, 170));
        BlacksmithLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BlacksmithLabel.setText("Blacksmith");
        BlacksmithLabel.setFocusable(false);
        BlacksmithLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        BlacksmithLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        BlacksmithContainer.add(BlacksmithLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        BlacksmithAddButton.setBackground(new java.awt.Color(255, 247, 214));
        BlacksmithAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        BlacksmithAddButton.setText(">");
        BlacksmithAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BlacksmithAddButton.setFocusable(false);
        BlacksmithAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BlacksmithAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BlacksmithAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        BlacksmithAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        BlacksmithAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        BlacksmithAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlacksmithAddButtonActionPerformed(evt);
            }
        });
        BlacksmithContainer.add(BlacksmithAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        BlacksmithAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        BlacksmithAmountLabel.setText("Amount");
        BlacksmithAmountLabel.setFocusable(false);
        BlacksmithContainer.add(BlacksmithAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        TannerContainer.setFocusable(false);
        TannerContainer.setOpaque(false);
        TannerContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TannerRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        TannerRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        TannerRemoveButton.setText("<");
        TannerRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TannerRemoveButton.setFocusable(false);
        TannerRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TannerRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        TannerRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        TannerRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        TannerRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        TannerRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TannerRemoveButtonActionPerformed(evt);
            }
        });
        TannerContainer.add(TannerRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        TannerLabel.setBackground(new java.awt.Color(156, 163, 170));
        TannerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TannerLabel.setText("Tanner");
        TannerLabel.setFocusable(false);
        TannerLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        TannerLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        TannerContainer.add(TannerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        TannerAddButton.setBackground(new java.awt.Color(255, 247, 214));
        TannerAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        TannerAddButton.setText(">");
        TannerAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TannerAddButton.setFocusable(false);
        TannerAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TannerAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        TannerAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        TannerAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        TannerAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        TannerAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TannerAddButtonActionPerformed(evt);
            }
        });
        TannerContainer.add(TannerAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        TannerAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        TannerAmountLabel.setText("Amount");
        TannerAmountLabel.setFocusable(false);
        TannerContainer.add(TannerAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        ApothecaryContainer.setFocusable(false);
        ApothecaryContainer.setOpaque(false);
        ApothecaryContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ApothecaryRemoveButton.setBackground(new java.awt.Color(255, 247, 214));
        ApothecaryRemoveButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ApothecaryRemoveButton.setText("<");
        ApothecaryRemoveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ApothecaryRemoveButton.setFocusable(false);
        ApothecaryRemoveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ApothecaryRemoveButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ApothecaryRemoveButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ApothecaryRemoveButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ApothecaryRemoveButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ApothecaryRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApothecaryRemoveButtonActionPerformed(evt);
            }
        });
        ApothecaryContainer.add(ApothecaryRemoveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 20, 20));

        ApothecaryLabel.setBackground(new java.awt.Color(156, 163, 170));
        ApothecaryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ApothecaryLabel.setText("Apothecary");
        ApothecaryLabel.setFocusable(false);
        ApothecaryLabel.setMaximumSize(new java.awt.Dimension(80, 20));
        ApothecaryLabel.setMinimumSize(new java.awt.Dimension(80, 20));
        ApothecaryContainer.add(ApothecaryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, 20));

        ApothecaryAddButton.setBackground(new java.awt.Color(255, 247, 214));
        ApothecaryAddButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ApothecaryAddButton.setText(">");
        ApothecaryAddButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ApothecaryAddButton.setFocusable(false);
        ApothecaryAddButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ApothecaryAddButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ApothecaryAddButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ApothecaryAddButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ApothecaryAddButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ApothecaryAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApothecaryAddButtonActionPerformed(evt);
            }
        });
        ApothecaryContainer.add(ApothecaryAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 20, 20));

        ApothecaryAmountLabel.setBackground(new java.awt.Color(156, 163, 170));
        ApothecaryAmountLabel.setText("Amount");
        ApothecaryAmountLabel.setFocusable(false);
        ApothecaryContainer.add(ApothecaryAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 100, 20));

        javax.swing.GroupLayout JobPanelLayout = new javax.swing.GroupLayout(JobPanel);
        JobPanel.setLayout(JobPanelLayout);
        JobPanelLayout.setHorizontalGroup(
            JobPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JobPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JobPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FarmerContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ForesterContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MinerContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BlacksmithContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TannerContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ApothecaryContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        JobPanelLayout.setVerticalGroup(
            JobPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JobPanelLayout.createSequentialGroup()
                .addComponent(FarmerContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ForesterContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MinerContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BlacksmithContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TannerContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ApothecaryContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LandLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LandLabel.setText("Land: Amount");

        LandBar.setForeground(new java.awt.Color(82, 58, 10));

        PopulationLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        PopulationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PopulationLabel.setText("Population: Amount");
        PopulationLabel.setPreferredSize(new java.awt.Dimension(180, 19));

        PopulationBar.setForeground(new java.awt.Color(237, 200, 164));
        PopulationBar.setPreferredSize(new java.awt.Dimension(180, 14));

        TownshipLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        TownshipLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TownshipLabel.setText("Township");

        ResourceButton.setBackground(new java.awt.Color(250, 209, 26));
        ResourceButton.setText("Gather Resources");
        ResourceButton.setMaximumSize(new java.awt.Dimension(180, 120));
        ResourceButton.setMinimumSize(new java.awt.Dimension(180, 120));
        ResourceButton.setPreferredSize(new java.awt.Dimension(180, 120));
        ResourceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResourceButtonActionPerformed(evt);
            }
        });

        AddWorkerButton.setBackground(new java.awt.Color(255, 247, 214));
        AddWorkerButton.setText("Create citizen");
        AddWorkerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddWorkerButtonActionPerformed(evt);
            }
        });

        UnemployedAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UnemployedAmountLabel.setText("Unemployed: Amount");

        WorkerCostLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WorkerCostLabel.setText("Citizen Cost:");

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(TownshipLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(JobPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddWorkerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(MainPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(UnemployedAmountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(WorkerCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addGap(0, 245, Short.MAX_VALUE)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PopulationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PopulationBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LandLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LandBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ResourceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(263, 263, 263))))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addComponent(TownshipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PopulationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PopulationBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LandLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LandBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(ResourceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddWorkerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UnemployedAmountLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(WorkerCostLabel))
                    .addComponent(JobPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        ConsoleOutputWindow.setEditable(false);
        ConsoleOutputWindow.setBackground(new java.awt.Color(228, 236, 248));
        ConsoleOutputWindow.setColumns(18);
        ConsoleOutputWindow.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        ConsoleOutputWindow.setRows(5);
        ConsoleOutputWindow.setAutoscrolls(false);
        ConsoleOutputWindow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ConsoleOutputWindow.setMaximumSize(new java.awt.Dimension(198, 80));
        ConsoleOutputWindow.setMinimumSize(new java.awt.Dimension(200, 80));
        ConsoleOutputWindowPanel.setViewportView(ConsoleOutputWindow);

        ClickResourcePanel.setBackground(new java.awt.Color(228, 236, 248));
        ClickResourcePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        MainResourceButtonGroup.add(FoodButton);
        FoodButton.setText("Food: ");
        FoodButton.setOpaque(false);
        FoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FoodButtonActionPerformed(evt);
            }
        });

        MainResourceButtonGroup.add(WoodButton);
        WoodButton.setText("Lumber: ");
        WoodButton.setOpaque(false);
        WoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WoodButtonActionPerformed(evt);
            }
        });

        MainResourceButtonGroup.add(StoneButton);
        StoneButton.setText("Stone: ");
        StoneButton.setOpaque(false);
        StoneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StoneButtonActionPerformed(evt);
            }
        });

        FoodBar.setForeground(new java.awt.Color(250, 209, 26));

        WoodBar.setForeground(new java.awt.Color(166, 108, 70));

        StoneBar.setForeground(new java.awt.Color(100, 100, 100));

        FoodAmountLabel.setText("Amount");

        WoodAmountLabel.setText("Amount");

        StoneAmountLabel.setText("Amount");

        javax.swing.GroupLayout ClickResourcePanelLayout = new javax.swing.GroupLayout(ClickResourcePanel);
        ClickResourcePanel.setLayout(ClickResourcePanelLayout);
        ClickResourcePanelLayout.setHorizontalGroup(
            ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FoodBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(WoodBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(StoneBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                        .addGroup(ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                                .addComponent(StoneButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoneAmountLabel))
                            .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                                .addComponent(FoodButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FoodAmountLabel))
                            .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                                .addComponent(WoodButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(WoodAmountLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ClickResourcePanelLayout.setVerticalGroup(
            ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClickResourcePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FoodButton)
                    .addComponent(FoodAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FoodBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WoodButton)
                    .addComponent(WoodAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WoodBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ClickResourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StoneButton)
                    .addComponent(StoneAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StoneBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ConsoleInputField.setBackground(new java.awt.Color(228, 236, 248));
        ConsoleInputField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ConsoleInputField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConsoleInputFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ConsoleInputFieldKeyReleased(evt);
            }
        });

        OptionsPanel.setBackground(new java.awt.Color(228, 236, 248));
        OptionsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SaveButton.setBackground(new java.awt.Color(255, 247, 214));
        SaveButton.setText("Save");
        SaveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        LoadButton.setBackground(new java.awt.Color(255, 247, 214));
        LoadButton.setText("Load");
        LoadButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadButtonActionPerformed(evt);
            }
        });

        RestartButton.setBackground(new java.awt.Color(255, 247, 214));
        RestartButton.setText("Restart");
        RestartButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        RestartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestartButtonActionPerformed(evt);
            }
        });

        BuySellButton.setBackground(new java.awt.Color(255, 247, 214));
        BuySellButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        BuySellButton.setText("MODE:BUY");
        BuySellButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BuySellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuySellButtonActionPerformed(evt);
            }
        });

        ModifierLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ModifierLabel.setText("Modifier");

        ModifierDecrementButton.setBackground(new java.awt.Color(255, 247, 214));
        ModifierDecrementButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ModifierDecrementButton.setText("<");
        ModifierDecrementButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ModifierDecrementButton.setFocusable(false);
        ModifierDecrementButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ModifierDecrementButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ModifierDecrementButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ModifierDecrementButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ModifierDecrementButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ModifierDecrementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierDecrementButtonActionPerformed(evt);
            }
        });

        ModifierIncrementButton.setBackground(new java.awt.Color(255, 247, 214));
        ModifierIncrementButton.setFont(new java.awt.Font("Gulim", 1, 12)); // NOI18N
        ModifierIncrementButton.setText(">");
        ModifierIncrementButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ModifierIncrementButton.setFocusable(false);
        ModifierIncrementButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ModifierIncrementButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ModifierIncrementButton.setMaximumSize(new java.awt.Dimension(43, 14));
        ModifierIncrementButton.setMinimumSize(new java.awt.Dimension(43, 14));
        ModifierIncrementButton.setPreferredSize(new java.awt.Dimension(43, 14));
        ModifierIncrementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierIncrementButtonActionPerformed(evt);
            }
        });

        ModifierNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ModifierNumberLabel.setText("1");

        javax.swing.GroupLayout OptionsPanelLayout = new javax.swing.GroupLayout(OptionsPanel);
        OptionsPanel.setLayout(OptionsPanelLayout);
        OptionsPanelLayout.setHorizontalGroup(
            OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OptionsPanelLayout.createSequentialGroup()
                        .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LoadButton, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                            .addComponent(RestartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(OptionsPanelLayout.createSequentialGroup()
                        .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(OptionsPanelLayout.createSequentialGroup()
                                .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(OptionsPanelLayout.createSequentialGroup()
                                        .addComponent(ModifierDecrementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ModifierNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ModifierIncrementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ModifierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(24, 24, 24))
                            .addGroup(OptionsPanelLayout.createSequentialGroup()
                                .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(BuySellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        OptionsPanelLayout.setVerticalGroup(
            OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OptionsPanelLayout.createSequentialGroup()
                .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OptionsPanelLayout.createSequentialGroup()
                        .addComponent(ModifierLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(OptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ModifierDecrementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierIncrementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierNumberLabel))
                        .addGap(18, 18, 18)
                        .addComponent(SaveButton))
                    .addGroup(OptionsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BuySellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RestartButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BuildingPanel.setBackground(new java.awt.Color(228, 236, 248));
        BuildingPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        HutCottageCabinHouseButton.setBackground(new java.awt.Color(255, 247, 214));
        HutCottageCabinHouseButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        HutCottageCabinHouseButton.setText("HCCH: Amount");
        HutCottageCabinHouseButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        HutCottageCabinHouseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HutCottageCabinHouseButtonActionPerformed(evt);
            }
        });

        FarmButton.setBackground(new java.awt.Color(255, 247, 214));
        FarmButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        FarmButton.setText("Farm: Amount");
        FarmButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FarmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FarmButtonActionPerformed(evt);
            }
        });

        SmitheyButton.setBackground(new java.awt.Color(255, 247, 214));
        SmitheyButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        SmitheyButton.setText("Smithey: Amount");
        SmitheyButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SmitheyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SmitheyButtonActionPerformed(evt);
            }
        });

        TanneryButton.setBackground(new java.awt.Color(255, 247, 214));
        TanneryButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        TanneryButton.setText("Tannery: Amount");
        TanneryButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TanneryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanneryButtonActionPerformed(evt);
            }
        });

        GraveyardButton.setBackground(new java.awt.Color(255, 247, 214));
        GraveyardButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        GraveyardButton.setText("Graveyard: Amount");
        GraveyardButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GraveyardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GraveyardButtonActionPerformed(evt);
            }
        });

        ChurchButton.setBackground(new java.awt.Color(255, 247, 214));
        ChurchButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ChurchButton.setText("Church: Amount");
        ChurchButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChurchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChurchButtonActionPerformed(evt);
            }
        });

        ApothecaryButton.setBackground(new java.awt.Color(255, 247, 214));
        ApothecaryButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ApothecaryButton.setText("Apothecary: Amount");
        ApothecaryButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ApothecaryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApothecaryButtonActionPerformed(evt);
            }
        });

        SchoolButton.setBackground(new java.awt.Color(255, 247, 214));
        SchoolButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        SchoolButton.setText("School: Amount");
        SchoolButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SchoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SchoolButtonActionPerformed(evt);
            }
        });

        HuntingLodgeButton.setBackground(new java.awt.Color(255, 247, 214));
        HuntingLodgeButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        HuntingLodgeButton.setText("Hunting Lodge: Amount");
        HuntingLodgeButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        HuntingLodgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HuntingLodgeButtonActionPerformed(evt);
            }
        });

        GreenhouseButton.setBackground(new java.awt.Color(255, 247, 214));
        GreenhouseButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        GreenhouseButton.setText("Greenhouse: Amount");
        GreenhouseButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GreenhouseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GreenhouseButtonActionPerformed(evt);
            }
        });

        BarnButton.setBackground(new java.awt.Color(255, 247, 214));
        BarnButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        BarnButton.setText("Barn: Amount");
        BarnButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BarnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarnButtonActionPerformed(evt);
            }
        });

        StorageButton.setBackground(new java.awt.Color(255, 247, 214));
        StorageButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        StorageButton.setText("Storage: Amount");
        StorageButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        StorageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StorageButtonActionPerformed(evt);
            }
        });

        StoneStockpileButton.setBackground(new java.awt.Color(255, 247, 214));
        StoneStockpileButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        StoneStockpileButton.setText("Stone Stockpile: Amount");
        StoneStockpileButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        StoneStockpileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StoneStockpileButtonActionPerformed(evt);
            }
        });

        LumberyardButton.setBackground(new java.awt.Color(255, 247, 214));
        LumberyardButton.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        LumberyardButton.setText("Lumber Yard: Amount");
        LumberyardButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LumberyardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LumberyardButtonActionPerformed(evt);
            }
        });

        HutCottageCabinHouseCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        HutCottageCabinHouseCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        FarmCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        FarmCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        SmitheyCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        SmitheyCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        TanneryCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        TanneryCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        GraveyardCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        GraveyardCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        ChurchCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        ChurchCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        ApothecaryCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        ApothecaryCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        SchoolCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        SchoolCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        HuntingLodgeCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        HuntingLodgeCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        GreenhouseCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        GreenhouseCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        BarnCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        BarnCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        StorageCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        StorageCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        LumberyardCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        LumberyardCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        StoneStockpileCostLabel.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        StoneStockpileCostLabel.setText("Cost: W:NNNDDD S:NNNDDD L:NNNDDD");

        javax.swing.GroupLayout BuildingPanelLayout = new javax.swing.GroupLayout(BuildingPanel);
        BuildingPanel.setLayout(BuildingPanelLayout);
        BuildingPanelLayout.setHorizontalGroup(
            BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuildingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addComponent(HutCottageCabinHouseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HutCottageCabinHouseCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addComponent(FarmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FarmCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addComponent(SmitheyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SmitheyCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addComponent(TanneryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TanneryCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addComponent(GraveyardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GraveyardCostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BuildingPanelLayout.createSequentialGroup()
                        .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(ApothecaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ApothecaryCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(HuntingLodgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HuntingLodgeCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(GreenhouseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GreenhouseCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(BarnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BarnCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(StorageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StorageCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(LumberyardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LumberyardCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(ChurchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ChurchCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(SchoolButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SchoolCostLabel))
                            .addGroup(BuildingPanelLayout.createSequentialGroup()
                                .addComponent(StoneStockpileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoneStockpileCostLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BuildingPanelLayout.setVerticalGroup(
            BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuildingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HutCottageCabinHouseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HutCottageCabinHouseCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FarmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FarmCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SmitheyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SmitheyCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TanneryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanneryCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GraveyardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GraveyardCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChurchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChurchCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ApothecaryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ApothecaryCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SchoolButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SchoolCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HuntingLodgeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HuntingLodgeCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GreenhouseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GreenhouseCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BarnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BarnCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StorageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StorageCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StoneStockpileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StoneStockpileCostLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BuildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LumberyardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LumberyardCostLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClickResourcePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BuildingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TabPanel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ConsoleInputField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConsoleOutputWindowPanel)
                    .addComponent(OptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ConsoleOutputWindowPanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConsoleInputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuildingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TabPanel)
                    .addComponent(ClickResourcePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HutCottageCabinHouseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HutCottageCabinHouseButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.hut, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.hut, modifieramount, game.rm, game.pm);
        }
        HutCottageCabinHouseButton.setText(game.bm.hut.getName() + ": " + game.bm.hut.displayAmount());
        HutCottageCabinHouseCostLabel.setText(game.bm.hut.displayCost());
    }//GEN-LAST:event_HutCottageCabinHouseButtonActionPerformed

    private void FarmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FarmButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.farm, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.farm, modifieramount, game.rm, game.pm);
        }
        FarmButton.setText(game.bm.farm.getName() + ": " + game.bm.farm.displayAmount());
        FarmCostLabel.setText(game.bm.farm.displayCost());
    }//GEN-LAST:event_FarmButtonActionPerformed

    private void SmitheyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SmitheyButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.smithey, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.smithey, modifieramount, game.rm, game.pm);
        }
        SmitheyButton.setText(game.bm.smithey.getName() + ": " + game.bm.smithey.displayAmount());
        SmitheyCostLabel.setText(game.bm.smithey.displayCost());
    }//GEN-LAST:event_SmitheyButtonActionPerformed

    private void TanneryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanneryButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.tannery, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.tannery, modifieramount, game.rm, game.pm);
        }
        TanneryButton.setText(game.bm.tannery.getName() + ": " + game.bm.tannery.displayAmount());
        TanneryCostLabel.setText(game.bm.tannery.displayCost());
    }//GEN-LAST:event_TanneryButtonActionPerformed

    private void GraveyardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GraveyardButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.graveyard, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.graveyard, modifieramount, game.rm, game.pm);
        }
        GraveyardButton.setText(game.bm.graveyard.getName() + ": " + game.bm.graveyard.displayAmount());
        GraveyardCostLabel.setText(game.bm.graveyard.displayCost());
    }//GEN-LAST:event_GraveyardButtonActionPerformed

    private void ChurchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChurchButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.church, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.church, modifieramount, game.rm, game.pm);
        }
        ChurchButton.setText(game.bm.church.getName() + ": " + game.bm.church.displayAmount());
        ChurchCostLabel.setText(game.bm.church.displayCost());
    }//GEN-LAST:event_ChurchButtonActionPerformed

    private void ApothecaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApothecaryButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.apothecary, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.apothecary, modifieramount, game.rm, game.pm);
        }
        ApothecaryButton.setText(game.bm.apothecary.getName() + ": " + game.bm.apothecary.displayAmount());
        ApothecaryCostLabel.setText(game.bm.apothecary.displayCost());
    }//GEN-LAST:event_ApothecaryButtonActionPerformed

    private void SchoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SchoolButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.school, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.school, modifieramount, game.rm, game.pm);
        }
        SchoolButton.setText(game.bm.school.getName() + ": " + game.bm.school.displayAmount());
        SchoolCostLabel.setText(game.bm.school.displayCost());
    }//GEN-LAST:event_SchoolButtonActionPerformed

    private void HuntingLodgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HuntingLodgeButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.huntinglodge, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.huntinglodge, modifieramount, game.rm, game.pm);
        }
        HuntingLodgeButton.setText(game.bm.huntinglodge.getName() + ": " + game.bm.huntinglodge.displayAmount());
        HuntingLodgeCostLabel.setText(game.bm.huntinglodge.displayCost());
    }//GEN-LAST:event_HuntingLodgeButtonActionPerformed

    private void GreenhouseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GreenhouseButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.greenhouse, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.greenhouse, modifieramount, game.rm, game.pm);
        }
        GreenhouseButton.setText(game.bm.greenhouse.getName() + ": " + game.bm.greenhouse.displayAmount());
        GreenhouseCostLabel.setText(game.bm.greenhouse.displayCost());
    }//GEN-LAST:event_GreenhouseButtonActionPerformed

    private void BarnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarnButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.barn, modifieramount, game.rm);
        } else {
            game.bm.removeBuilding(game.bm.barn, modifieramount, game.rm);
        }
        BarnButton.setText(game.bm.barn.getName() + ": " + game.bm.barn.displayAmount());
        BarnCostLabel.setText(game.bm.barn.displayCost());
    }//GEN-LAST:event_BarnButtonActionPerformed

    private void StorageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StorageButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.storagestockpile, modifieramount, game.rm);
        } else {
            game.bm.removeBuilding(game.bm.storagestockpile, modifieramount, game.rm);
        }
        StorageButton.setText(game.bm.storagestockpile.getName() + ": " + game.bm.storagestockpile.displayAmount());
        StorageCostLabel.setText(game.bm.storagestockpile.displayCost());
    }//GEN-LAST:event_StorageButtonActionPerformed

    private void StoneStockpileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StoneStockpileButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.stonestockpile, modifieramount, game.rm);
        } else {
            game.bm.removeBuilding(game.bm.stonestockpile, modifieramount, game.rm);
        }
        StoneStockpileButton.setText(game.bm.stonestockpile.getName() + ": " + game.bm.stonestockpile.displayAmount());
        StoneStockpileCostLabel.setText(game.bm.stonestockpile.displayCost());
    }//GEN-LAST:event_StoneStockpileButtonActionPerformed

    private void LumberyardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LumberyardButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.lumberyard, modifieramount, game.rm);
        } else {
            game.bm.removeBuilding(game.bm.lumberyard, modifieramount, game.rm);
        }
        LumberyardButton.setText(game.bm.lumberyard.getName() + ": " + game.bm.lumberyard.displayAmount());
        LumberyardCostLabel.setText(game.bm.lumberyard.displayCost());
    }//GEN-LAST:event_LumberyardButtonActionPerformed

    private void TradingPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TradingPostButtonActionPerformed
        if (game.bm.tradingpost.getAmount() != 1) {
            game.bm.addBuilding(game.bm.tradingpost, 1, game.rm);
            if (game.bm.tradingpost.getAmount() >= 1) {
                TradingPostButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_TradingPostButtonActionPerformed

    private void BuySellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuySellButtonActionPerformed
        buy = !buy;
        if (buy) {
            BuySellButton.setText("Mode:BUY");
        } else {
            BuySellButton.setText("Mode:SELL");
        }
    }//GEN-LAST:event_BuySellButtonActionPerformed

    private void WoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WoodButtonActionPerformed
        ResourceButton.setText("Chop Wood!");
        ResourceButton.setBackground(new java.awt.Color(166, 108, 70));
    }//GEN-LAST:event_WoodButtonActionPerformed

    private void BarracksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarracksButtonActionPerformed
        if (buy) {
            game.bm.addBuilding(game.bm.barracks, modifieramount, game.rm, game.pm);
        } else {
            game.bm.removeBuilding(game.bm.barracks, modifieramount, game.rm, game.pm);
        }
        BarracksButton.setText(game.bm.barracks.getName() + ": " + game.bm.barracks.displayAmount());
        BarracksCostLabel.setText(game.bm.barracks.displayCost());
    }//GEN-LAST:event_BarracksButtonActionPerformed

    private void ResourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResourceButtonActionPerformed
        if (FoodButton.isSelected()) {
            game.rm.ClickMultiplierUpdater(game.pm, game.rm.food.getGatherMultiplier());
            game.rm.ClickFood(0, game.pm);
        } else if (WoodButton.isSelected()) {
            game.rm.ClickMultiplierUpdater(game.pm, game.rm.wood.getGatherMultiplier());
            game.rm.ClickWood(0, game.pm);
        } else if (StoneButton.isSelected()) {
            game.rm.ClickMultiplierUpdater(game.pm, game.rm.stone.getGatherMultiplier());
            game.rm.ClickStone(0, game.pm);
        } else {
            Console.println("No radio button selected! How'd that happen?");
        }
    }//GEN-LAST:event_ResourceButtonActionPerformed

    private void AddWorkerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddWorkerButtonActionPerformed
        game.pm.AddWorker(game.pm.unemployed, modifieramount, game.rm);
    }//GEN-LAST:event_AddWorkerButtonActionPerformed

    private void FarmerRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FarmerRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.farmer, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_FarmerRemoveButtonActionPerformed

    private void ForesterRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForesterRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.forester, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_ForesterRemoveButtonActionPerformed

    private void MinerRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinerRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.miner, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_MinerRemoveButtonActionPerformed

    private void BlacksmithRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlacksmithRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.blacksmith, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_BlacksmithRemoveButtonActionPerformed

    private void TannerRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TannerRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.tanner, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_TannerRemoveButtonActionPerformed

    private void ApothecaryRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApothecaryRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.apothecary, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_ApothecaryRemoveButtonActionPerformed

    private void FarmerAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FarmerAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.farmer, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_FarmerAddButtonActionPerformed

    private void ForesterAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForesterAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.forester, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_ForesterAddButtonActionPerformed

    private void MinerAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinerAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.miner, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_MinerAddButtonActionPerformed

    private void ApothecaryAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApothecaryAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.apothecary, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_ApothecaryAddButtonActionPerformed

    private void TannerAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TannerAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.tanner, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_TannerAddButtonActionPerformed

    private void BlacksmithAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlacksmithAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.blacksmith, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_BlacksmithAddButtonActionPerformed

    private void SoldierRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoldierRemoveButtonActionPerformed
        game.pm.RemoveWorker(CivClicker.game.pm.soldier, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_SoldierRemoveButtonActionPerformed

    private void SoldierAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoldierAddButtonActionPerformed
        game.pm.AddWorker(CivClicker.game.pm.soldier, modifieramount, CivClicker.game.rm);
    }//GEN-LAST:event_SoldierAddButtonActionPerformed

    private void ModifierDecrementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierDecrementButtonActionPerformed
        if (modifierindexpoint != 0) {
            --modifierindexpoint;
            modifieramount = modifiernumbers[modifierindexpoint];
            ModifierNumberLabel.setText(Integer.toString(modifiernumbers[modifierindexpoint]));
        }
    }//GEN-LAST:event_ModifierDecrementButtonActionPerformed

    private void ModifierIncrementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierIncrementButtonActionPerformed
        if (modifierindexpoint != 3) {
            ++modifierindexpoint;
            modifieramount = modifiernumbers[modifierindexpoint];
            ModifierNumberLabel.setText(Integer.toString(modifiernumbers[modifierindexpoint]));
        }
    }//GEN-LAST:event_ModifierIncrementButtonActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        CivClicker.Save();
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void LoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadButtonActionPerformed
        game = CivClicker.Load();
        initBuildingdisplay(game.bm);
    }//GEN-LAST:event_LoadButtonActionPerformed

    private void RestartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestartButtonActionPerformed
        game = CivClicker.Restart();
        initBuildingdisplay(game.bm);
    }//GEN-LAST:event_RestartButtonActionPerformed

    private void ConsoleInputFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConsoleInputFieldKeyPressed
        if (!inputkeypressed) {
            inputkeypressed = true;
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    if (ConsoleInputField.getText().trim().length() != 0) {
                        inputhistory.add(ConsoleInputField.getText());
                        Devmode.ComanndLookup(ConsoleInputField.getText());
                        inputhistoryindexpoint = inputhistory.size();
                    }
                    ConsoleInputField.setText("");
                    break;
                case KeyEvent.VK_UP:
                    if (inputhistoryindexpoint - 1 >= 0) {
                        inputhistoryindexpoint--;
                    }
                    ConsoleInputField.setText(inputhistory.get(inputhistoryindexpoint));
                    break;
                case KeyEvent.VK_DOWN:
                    if (inputhistoryindexpoint + 1 < inputhistory.size()) {
                        inputhistoryindexpoint++;
                    }
                    ConsoleInputField.setText(inputhistory.get(inputhistoryindexpoint));
            }
        }
    }//GEN-LAST:event_ConsoleInputFieldKeyPressed

    private void ConsoleInputFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConsoleInputFieldKeyReleased
        if (inputkeypressed) {
            inputkeypressed = false;
        }
    }//GEN-LAST:event_ConsoleInputFieldKeyReleased

    private void FoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoodButtonActionPerformed
        ResourceButton.setText("Gather Food!");
        ResourceButton.setBackground(new java.awt.Color(250, 209, 26));
    }//GEN-LAST:event_FoodButtonActionPerformed

    private void StoneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StoneButtonActionPerformed
        ResourceButton.setText("Mine Stone!");
        ResourceButton.setBackground(new java.awt.Color(100, 100, 100));
    }//GEN-LAST:event_StoneButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AchievementsPanel;
    private javax.swing.JButton AddWorkerButton;
    private javax.swing.JButton ApothecaryAddButton;
    private javax.swing.JLabel ApothecaryAmountLabel;
    private javax.swing.JButton ApothecaryButton;
    private javax.swing.JPanel ApothecaryContainer;
    private javax.swing.JPanel ApothecaryContainer1;
    private javax.swing.JLabel ApothecaryCostLabel;
    private javax.swing.JLabel ApothecaryLabel;
    private javax.swing.JButton ApothecaryRemoveButton;
    private javax.swing.JButton BarnButton;
    private javax.swing.JLabel BarnCostLabel;
    private javax.swing.JButton BarracksButton;
    private javax.swing.JLabel BarracksCostLabel;
    private javax.swing.JButton BlacksmithAddButton;
    private javax.swing.JLabel BlacksmithAmountLabel;
    private javax.swing.JPanel BlacksmithContainer;
    private javax.swing.JLabel BlacksmithLabel;
    private javax.swing.JButton BlacksmithRemoveButton;
    private javax.swing.JPanel BuildingPanel;
    private javax.swing.JButton BuySellButton;
    private javax.swing.JButton ChurchButton;
    private javax.swing.JLabel ChurchCostLabel;
    private javax.swing.JPanel ClickResourcePanel;
    private javax.swing.JTextField ConsoleInputField;
    private javax.swing.JTextArea ConsoleOutputWindow;
    private javax.swing.JScrollPane ConsoleOutputWindowPanel;
    private javax.swing.JPanel ExpansionPanel;
    private javax.swing.JButton FarmButton;
    private javax.swing.JLabel FarmCostLabel;
    private javax.swing.JButton FarmerAddButton;
    private javax.swing.JLabel FarmerAmountLabel;
    private javax.swing.JPanel FarmerContainer;
    private javax.swing.JLabel FarmerLabel;
    private javax.swing.JButton FarmerRemoveButton;
    private javax.swing.JLabel FoodAmountLabel;
    private javax.swing.JProgressBar FoodBar;
    private javax.swing.JRadioButton FoodButton;
    private javax.swing.JButton ForesterAddButton;
    private javax.swing.JLabel ForesterAmountLabel;
    private javax.swing.JPanel ForesterContainer;
    private javax.swing.JLabel ForesterLabel;
    private javax.swing.JButton ForesterRemoveButton;
    private javax.swing.JButton GraveyardButton;
    private javax.swing.JLabel GraveyardCostLabel;
    private javax.swing.JButton GreenhouseButton;
    private javax.swing.JLabel GreenhouseCostLabel;
    private javax.swing.JLabel HerbsAmountLabel;
    private javax.swing.JButton HuntingLodgeButton;
    private javax.swing.JLabel HuntingLodgeCostLabel;
    private javax.swing.JButton HutCottageCabinHouseButton;
    private javax.swing.JLabel HutCottageCabinHouseCostLabel;
    private javax.swing.JPanel JobPanel;
    private javax.swing.JProgressBar LandBar;
    private javax.swing.JLabel LandLabel;
    private javax.swing.JLabel LeatherAmountLabel;
    private javax.swing.JButton LoadButton;
    private javax.swing.JButton LumberyardButton;
    private javax.swing.JLabel LumberyardCostLabel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.ButtonGroup MainResourceButtonGroup;
    private javax.swing.JLabel MetalAmountLabel;
    private javax.swing.JButton MinerAddButton;
    private javax.swing.JLabel MinerAmountLabel;
    private javax.swing.JPanel MinerContainer;
    private javax.swing.JLabel MinerLabel;
    private javax.swing.JButton MinerRemoveButton;
    private javax.swing.JButton ModifierDecrementButton;
    private javax.swing.JButton ModifierIncrementButton;
    private javax.swing.JLabel ModifierLabel;
    private javax.swing.JLabel ModifierNumberLabel;
    private javax.swing.JPanel OptionsPanel;
    private javax.swing.JLabel OreAmountLabel;
    private javax.swing.JProgressBar PopulationBar;
    private javax.swing.JLabel PopulationLabel;
    private javax.swing.JPanel ReligionPanel;
    private javax.swing.JButton ResourceButton;
    private javax.swing.JPanel ResourcesPanel;
    private javax.swing.JButton RestartButton;
    private javax.swing.JButton SaveButton;
    private javax.swing.JButton SchoolButton;
    private javax.swing.JLabel SchoolCostLabel;
    private javax.swing.JLabel SharedResourceAmountLabel;
    private javax.swing.JProgressBar SharedResourceStorageBar;
    private javax.swing.JLabel SharedResourceStorageLabel;
    private javax.swing.JLabel SkinsAmountLabel;
    private javax.swing.JButton SmitheyButton;
    private javax.swing.JLabel SmitheyCostLabel;
    private javax.swing.JButton SoldierAddButton;
    private javax.swing.JLabel SoldierAmountLabel;
    private javax.swing.JLabel SoldierLabel;
    private javax.swing.JButton SoldierRemoveButton;
    private javax.swing.JLabel StoneAmountLabel;
    private javax.swing.JProgressBar StoneBar;
    private javax.swing.JRadioButton StoneButton;
    private javax.swing.JButton StoneStockpileButton;
    private javax.swing.JLabel StoneStockpileCostLabel;
    private javax.swing.JButton StorageButton;
    private javax.swing.JLabel StorageCostLabel;
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JButton TannerAddButton;
    private javax.swing.JLabel TannerAmountLabel;
    private javax.swing.JPanel TannerContainer;
    private javax.swing.JLabel TannerLabel;
    private javax.swing.JButton TannerRemoveButton;
    private javax.swing.JButton TanneryButton;
    private javax.swing.JLabel TanneryCostLabel;
    private javax.swing.JLabel TownshipLabel;
    private javax.swing.JPanel TradingPanel;
    private javax.swing.JButton TradingPostButton;
    private javax.swing.JLabel TradingPostCostLabel;
    private javax.swing.JLabel UnemployedAmountLabel;
    private javax.swing.JPanel UpgradesPanel;
    private javax.swing.JLabel WoodAmountLabel;
    private javax.swing.JProgressBar WoodBar;
    private javax.swing.JRadioButton WoodButton;
    private javax.swing.JLabel WorkerCostLabel;
    // End of variables declaration//GEN-END:variables
}
