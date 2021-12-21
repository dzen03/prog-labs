package com.dzen03;

import com.dzen03.core.PlaneCrashException;
import com.dzen03.subjects.*;

import java.lang.*;


interface Prog
{
    void start();
}

public class Main{


    public static void main (String[] args) {

        Prog prog = new Prog() {  // TODO abstract class
            @Override
            public void start() {
                Me me = new Me();
                Us us = new Us();
                Danford danford = new Danford();

                us.view();
                us.rememberPaintings();

                View view = new View();
                view.beSimilar();

                Giants giants = new Giants();
                giants.stand();

//        Гадать, насколько выше они были тогда, представлялось бессмысленным,
//        хотя по всем приметам некие особые атмосферные условия в этом таинственном районе препятствовали переменам,
//        сдерживая обычный процесс разрушения горных пород.

                Faceless faceless = new Faceless();
                faceless.guess();

                Conditions conditions = new Conditions();
                conditions.preventHold();

                Imagination imagination = new Imagination();
                imagination.worryTease();

//        Денфорт вел самолет,
                // TODO add exception handler
                try
                {
                    danford.pilotPlane();
                }
                catch (PlaneCrashException exception)
                {
                    System.out.println(exception.getMessage());
                    return;
                }

                ((Me) me.see()).shoot();

                me.swap();

                danford.see();

                me.beNotAsGood();

                Compositions compositions = new Compositions();

                compositions.beOf();
                compositions.scareAndWorry();


                Edges edges = new Edges();
                edges.crumbleAndRound();

                StoneStrength stoneStrength = new StoneStrength();
                stoneStrength.help_to_stay();

                Cubes cubes = new Cubes();
                cubes.seem();

                This this_ = new This();
                this_.be_like();


                us.think();

                ((Me) me.guess()).feel();
            }
        };

        prog.start();

//      края этих каменных фигур за долгие годы искрошились и закруглились
//		но исключительная прочность камня помогла ему выстоять. Нижние, примыкающие к склону части кубов казались схожими с породами хребтов.
//      Все вместе это напоминало развалины Мачу Пикчу в Андах или крепостные стены Киша, обнаруженные археологической экспедицией Оксфордского музея под открытым небом.
//      Нам с Денфортом несколько раз почудилось, что все эти конструкции состоят из отдельных гигантских глыб,
//      я не понимал Какое объяснение можно дать этому  и чувствовал себя как геолог посрамленным.

//        а я рассматривал их в бинокль, то и дело щелкая аэрокамерой и
//        иногда замещая у руля своего товарища,
//        чтобы дать и ему возможность полюбоваться в бинокль на все эти диковины.
//        Впрочем, ненадолго, ибо мое искусство пилотирования оставляло желать лучшего.
//        Мы уже поняли, что странные композиции состояли по большей части из легкого архейского кварцита,
//        которого больше нигде вокруг не было, а удивительная равномерность их чередования пугала и настораживала нас, как и беднягу Лейка.
    }
}

//  ID 181818