/**
 * @author ByoungHoon Kim
 * http://blue021.tistory.com/JQueryRollChildren
 *
 * Version 0.1
 * Copyright (c) 2011, ByoungHoon, Kim
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/mit-license.php
 *
 */


(function( $ ){

    var methods = {
        start : 
            function(options){ 
                return this.each(function(){
                    var $this = $(this);
                    if( true == $this.data('rolling') ) return; 

                    var opts = $.extend({}, $.fn.rollchildren.defaults, options);
                    if( 0 == opts.height )
                        opts.height = $this.children().first().height();

                    if( true == opts.pause_on_mouseover )
                    {
                        $this.hover(
                            function(){
                                $(this).rollchildren('pause', true);
                            },
                            function(){
                                $(this).rollchildren('pause', false);
                            });
                    }

                    if (null == $this.data('showing_item'))
                    {
                        $this.css({
                            'position' : 'relative', 
                            'height':  opts.height
                        });

                        $this.children().css({
                            'position' : 'absolute', 
                            'top' : opts.height, 
                            'opacity':0});
                    }

                    $this.data('rolling', true);
                    $this.rollchildren('conti', opts);
                });
            },


        stop  : function(){
                    return this.each(function(){
                        var $this = $(this);
                        $this.data('rolling', false);
                    });
                },


        pause : function(b){
                    return this.each(function(){
                        var $this = $(this);
                        $this.data('pause', b);
                    });
                },


        conti : function(options){
                    return this.each(function(){
                        var $this = $(this);
                        var first_item = $this.children().first();
                        var last_item =  $this.children().last();


                        if( false == $this.data('rolling') )
                        {
                            return;
                        }


                        if( true == $this.data('pause') )
                        {
                            setTimeout( 
                                    function(){
                                        $this.rollchildren('conti', options)},
                                        1000 );
                            return;
                        }
                        var next_item = null;
                        if ($this.data('showing_item'))
                        {
                            $this.data('showing_item').clearQueue();
                            $this.data('showing_item').animate(
                                    options.roll_up_old_item?{'top' : -options.height, 'opacity':0}:{'opacity':0},
                                    options.speed,
                                    'swing',
                                    function(){
                                        $(this).css({
                                            'top' : options.height 
                                        });
                                    });
                            next_item = $this.data('showing_item').next();
                        }

                        if(null == next_item || null == next_item.html() )
                            next_item = first_item;

                        next_item.clearQueue();
                        next_item.animate(
                                {'top' : 0, 'opacity':1},
                                options.speed,
                                'swing'
                                );
                        $this.data('showing_item', next_item );

                        if( 1 >= $this.children().length )
                            return;

                        if( options.loop == false )
                        {
                            if( $this.data('showing_item').get(0) === last_item.get(0) )
                                return;
                        }

                        setTimeout( 
                                function(){
                                    $this.rollchildren('conti', options)},
                                options.delay_time );
                    });
             }

        };

        $.fn.rollchildren = function(method, options){
            if( methods[method] ){
                return methods[method].apply( this, Array.prototype.slice.call(arguments, 1));
            } else if ( typeof method == 'object' || !method ){
                return methods.start.apply( this, arguments );
            } else {
                $.error( 'Method ' + method + ' does not exist on jQuery.rollchildren'  );
            }
        };

        $.fn.rollchildren.defaults = {
            delay_time : 3000,            // item showing delay time (millisecond).
            loop : true,                  // If true, Loop on last item to first item.
            pause_on_mouseover : true,    // If true, Puase on Mouseover.
            roll_up_old_item : true,      // if ture, roll up old item.  
            speed: 'slow',                // itme moving speed, (slow or fast).
            height : 0
        };

    })( jQuery );
