package com.paul.song.analytics.plugin

import org.objectweb.asm.Opcodes

class ShadowlessHookConfig {

    public final static HashMap<String, ShadowlessMethodCell> sInterfaceMethods = new HashMap<>()
    public final static HashMap<String, ShadowlessMethodCell> sClassMethods = new HashMap<>()
    public final static HashSet<ShadowlessMethodCell> sOnlyMethods = new HashMap<>()
    public final static HashSet<ShadowlessMethodCell> sLifecycleMethods = new HashMap<>()

    static {

        /***************************************** 接口方法 ****************************************/

        ShadowlessMethodCell onClick = new ShadowlessMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'Landroid/view/View$OnClickListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        sInterfaceMethods.put(onClick.parent + onClick.name + onClick.desc, onClick)
        ShadowlessMethodCell onCheckedChanged = new ShadowlessMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'Landroid/widget/CompoundButton$OnCheckedChangeListener;',
                'trackViewOnClick',
                '(Landroid/widget/CompoundButton;Z)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onCheckedChanged.parent + onCheckedChanged.name + onCheckedChanged.desc, onCheckedChanged)

        ShadowlessMethodCell onRatingChanged = new ShadowlessMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'Landroid/widget/RatingBar$OnRatingBarChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        sInterfaceMethods.put(onRatingChanged.parent + onRatingChanged.name + onRatingChanged.desc, onRatingChanged)


        ShadowlessMethodCell onStopTrackingTouch = new ShadowlessMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'Landroid/widget/SeekBar$OnSeekBarChangeListener;',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD])
        sInterfaceMethods.put(onStopTrackingTouch.parent + onStopTrackingTouch.name + onStopTrackingTouch.desc, onStopTrackingTouch)

        ShadowlessMethodCell onClickDialog = new ShadowlessMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'Landroid/content/DialogInterface$OnClickListener;',
                'trackViewOnClick',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onClickDialog.parent + onClickDialog.name + onClickDialog.desc, onClickDialog)

        ShadowlessMethodCell onClickDialog1 = new ShadowlessMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;IZ)V',
                'Landroid/content/DialogInterface$OnMultiChoiceClickListener;',
                'trackViewOnClick',
                '(Landroid/content/DialogInterface;IZ)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onClickDialog1.parent + onClickDialog1.name + onClickDialog1.desc, onClickDialog1)

        ShadowlessMethodCell onItemClick = new ShadowlessMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'Landroid/widget/AdapterView$OnItemClickListener;',
                'trackViewOnClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onItemClick.parent + onItemClick.name + onItemClick.desc, onItemClick)

        ShadowlessMethodCell onGroupClick = new ShadowlessMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'Landroid/widget/ExpandableListView$OnGroupClickListener;',
                'trackExpandableListViewGroupOnClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onGroupClick.parent + onGroupClick.name + onGroupClick.desc, onGroupClick)

        ShadowlessMethodCell onChildClick = new ShadowlessMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'Landroid/widget/ExpandableListView$OnChildClickListener;',
                'trackExpandableListViewChildOnClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onChildClick.parent + onChildClick.name + onChildClick.desc, onChildClick)

        ShadowlessMethodCell onTabChanged = new ShadowlessMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'Landroid/widget/TabHost$OnTabChangeListener;',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD])
        sInterfaceMethods.put(onTabChanged.parent + onTabChanged.name + onTabChanged.desc, onTabChanged)

        ShadowlessMethodCell onCheckedChangedRadioGroup = new ShadowlessMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'Landroid/widget/RadioGroup$OnCheckedChangeListener;',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        sInterfaceMethods.put(onCheckedChangedRadioGroup.parent + onCheckedChangedRadioGroup.name + onCheckedChangedRadioGroup.desc, onCheckedChangedRadioGroup)

        // Todo: 扩展



        /************************************* 类方法（非接口） *************************************/

        ShadowlessMethodCell play = new ShadowlessMethodCell(
                'play',
                '(Lcom/paul/song/medialibrary/entity/TrackEntity;)V',
                'Lcom/paul/song/medialibrary/player/AudioPlayer;',
                'trackPlay',
                '(Lcom/paul/song/medialibrary/entity/TrackEntity;)V',
                1, 1,
                [Opcodes.ALOAD])
        sClassMethods.put(play.parent + play.name + play.desc, play)

        ShadowlessMethodCell dispatchOnPageSelected = new ShadowlessMethodCell(
                'dispatchOnPageSelected',
                '(I)V',
                'Landroid/support/v4/view/ViewPager;',
                'trackViewPager',
                '(Landroid/support/v4/view/ViewPager;I)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD])
        sClassMethods.put(dispatchOnPageSelected.parent + dispatchOnPageSelected.name + dispatchOnPageSelected.desc, dispatchOnPageSelected)

        ShadowlessMethodCell dispatchTabSelected = new ShadowlessMethodCell(
                'dispatchTabSelected',
                '(Landroid/support/design/widget/TabLayout$Tab;)V',
                'Landroid/support/design/widget/TabLayout;',
                'trackTabLayout',
                '(Landroid/support/design/widget/TabLayout;Landroid/support/design/widget/TabLayout$Tab;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD])
        sClassMethods.put(dispatchTabSelected.parent + dispatchTabSelected.name + dispatchTabSelected.desc, dispatchTabSelected)

        ShadowlessMethodCell invokeOnTabChangeListener = new ShadowlessMethodCell(
                'invokeOnTabChangeListener',
                '()V',
                'Landroid/widget/TabHost;',
                'trackTabHostWhole',
                '(Landroid/widget/TabHost;)V',
                0, 1,
                [Opcodes.ALOAD])
        sClassMethods.put(invokeOnTabChangeListener.parent + invokeOnTabChangeListener.name + invokeOnTabChangeListener.desc, invokeOnTabChangeListener)
        // Todo: 扩展



        /***************************************** 纯方法 ******************************************/

        // onContextItemSelected
        ShadowlessMethodCell onContextItemSelected = new ShadowlessMethodCell(
                'onContextItemSelected',
                '(Landroid/view/MenuItem;)Z',
                '', // 不确定
                'trackViewOnClick',
                '(Ljava/lang/Object;Landroid/view/MenuItem;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD])
        sOnlyMethods.add(onContextItemSelected)

        // onOptionsItemSelected
        ShadowlessMethodCell onOptionsItemSelected = new ShadowlessMethodCell(
                'onOptionsItemSelected',
                '(Landroid/view/MenuItem;)Z',
                '', // 不确定
                'trackViewOnClick',
                '(Ljava/lang/Object;Landroid/view/MenuItem;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD])
        sOnlyMethods.add(onOptionsItemSelected)

        // onStartCommand
//        ShadowlessMethodCell onStartCommand = new ShadowlessMethodCell(
//                'onStartCommand',
//                '(Landroid/content/Intent;II)I',
//                '', // 不确定
//                'trackIntentFromService',
//                '(Ljava/lang/Object;Landroid/content/Intent;)V',
//                0, 2,
//                [Opcodes.ALOAD, Opcodes.ALOAD])
//        sOnlyMethods.add(onStartCommand)

        // onStartCommand
//        ShadowlessMethodCell onHandleIntent = new ShadowlessMethodCell(
//                'onHandleIntent',
//                '(Landroid/content/Intent;)V',
//                '', // 不确定
//                'trackIntentFromService',
//                '(Ljava/lang/Object;Landroid/content/Intent;)V',
//                0, 2,
//                [Opcodes.ALOAD, Opcodes.ALOAD])
//        sOnlyMethods.add(onHandleIntent)

        // Todo: 扩展



        /*************************************** 生命周期方法 ***************************************/

        // onResume
        ShadowlessMethodCell onResume = new ShadowlessMethodCell(
                'onResume',
                '()V',
                '', // 不确定
                'trackLifecycleMethodOnResume',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD])
//        sLifecycleMethods.add(onResume)

        // onPause
        ShadowlessMethodCell onPause = new ShadowlessMethodCell(
                'onPause',
                '()V',
                '', // 不确定
                'trackLifecycleMethodOnPause',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD])
//        sLifecycleMethods.add(onPause)

        // Todo: 扩展



    }
}