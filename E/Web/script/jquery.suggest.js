	
	/*
	 *	jquery.suggest 1.1 - 2007-08-06
	 *	
	 *	Uses code and techniques from following libraries:
	 *	1. http://www.dyve.net/jquery/?autocomplete
	 *	2. http://dev.jquery.com/browser/trunk/plugins/interface/iautocompleter.js	
	 *
	 *	All the new stuff written by Peter Vulgaris (www.vulgarisoip.com)	
	 *	Feel free to do whatever you want with this file
	 * 
	 */
	 
	 /*modify by floyd jiang
	  *1. add suggest's options :
	  *     itemType :  decide the binding data of dropdownlist-style items ,which options are 'text' and 'json'
	  *                       if 'json': you have to set the 'displayText' option and the 'displayValue' option
	  *     displayText: when itemType set as 'json', displayText is used to specify the property  of the json object to be show
	 *      displayValue: when itemType set as 'json' displayValue is used to specify the property of the json object to be store the value like DropDownList
	 * 2. add the method 'parseJson(data)' : it is desgined to parse json array datasource which responsed as suject results
	 * 3. add the suggest's option : queryConditions,
	 */
	
	(function($) {

		$.suggest = function(input, options) {
	
			var $input = $(input).attr("autocomplete", "off");
			var $results = $(document.createElement("ul"));

			var timeout = false;		// hold timeout ID for suggestion results to appear	
			var prevLength = 0;			// last recorded length of $input.val()
			var cache = [];				// cache MRU list
			var cacheSize = 0;			// size of cache in chars (bytes?)
			
			$results.addClass(options.resultsClass).appendTo('body');
				

			resetPosition();
			$(window)
				.load(resetPosition)		// just in case user is changing size of page while loading
				.resize(resetPosition);

			$input.blur(function() {
				setTimeout(function() { $results.hide() }, 200);
			});
			
			
			// help IE users if possible
			try {
				$results = $results.bgiframe();
			} catch(e) {}


			// I really hate browser detection, but I don't see any other way
			if ($.browser.mozilla)
				$input.keypress(processKey);	// onkeypress repeats arrow keys in Mozilla/Opera
			else
				$input.keydown(processKey);		// onkeydown repeats arrow keys in IE/Safari
			
			//$input.change(inputChange);



			function resetPosition() {
				// requires jquery.dimension plugin
				var offset = $input.offset();
				$results.css({
					top: (offset.top + input.offsetHeight) + 'px',
					left: offset.left + 'px'
				});
			}
			
			
			function processKey(e) {
				
				// handling up/down/escape requires results to be visible
				// handling enter/tab requires that AND a result to be selected
				if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) ||
					(/^13$|^9$/.test(e.keyCode) && getCurrentResult())) {
		            
		            if (e.preventDefault)
		                e.preventDefault();
					if (e.stopPropagation)
		                e.stopPropagation();

					e.cancelBubble = true;
					e.returnValue = false;
				
					switch(e.keyCode) {
	
						case 38: // up
							prevResult();
							break;
				
						case 40: // down
							nextResult();
							break;
	
						case 9:  // tab
						case 13: // return
							selectCurrentResult();
							break;
							
						case 27: //	escape
							$results.hide();
							break;
	
					}
					
				} else if ($input.val().length != prevLength) {

					if (timeout) 
						clearTimeout(timeout);
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
					
				}			
					
				
			}
			
			function inputChange(e)
			{
			    if ($input.val().length != prevLength) {

					if (timeout) 
						clearTimeout(timeout);
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
					
				}		
			}
			
			
			function suggest() {
			
			var q = $.trim($input.val());

				if (q.length >= options.minchars) {
					
					cached = checkCache(q);
					
					if (cached) {
					
						displayItems(cached['items']);
						
					} else {
					
					    var params= {};
					    if(options.queryCondition)
					    {
					        params = options.queryCondition();
					    }
					    params["q"] = q;
						$.getJSON(options.source, params, function(data) {

							$results.hide();
														
							var items = [];
							if(options.itemType == "text")
							    items = parseTxt(data, q);
							 else 
							    items = parseJson(data,q);
							
							displayItems(items);
							addToCache(q, items, data.length);
							
						});
						
					}
					
				} else {
				
					$results.hide();
					
				}
					
			}
			
			
			function checkCache(q) {

				for (var i = 0; i < cache.length; i++)
					if (cache[i]['q'] == q) {
						cache.unshift(cache.splice(i, 1)[0]);
						return cache[0];
					}
				
				return false;
			
			}
			
			function addToCache(q, items, size) {
                
                if(!options.cached) return;
				while (cache.length && (cacheSize + size > options.maxCacheSize)) {
					var cached = cache.pop();
					cacheSize -= cached['size'];
				}
				
				cache.push({
					q: q,
					size: size,
					items: items
					});
					
				cacheSize += size;
			
			}
			
			function displayItems(items) {
				
				if (!items)
					return;
					
				if (!items.length) {
					$results.hide();
					return;
				}
				
				var txt = "";
				var val = "";
				
				var html = '';
				for (var i = 0; i < items.length; i++)
				{
				    if(options.itemType == "text")
				    	html += '<li>' + items[i] + '</li>';
				   else 
				        html += '<li value="'+ items[i][options.displayValue]  +'">' + items[i][options.displayText] + '</li>';
                }
                
                
				$results.html(html).show();
				
				$results
					.children('li')
					.mouseover(function() {
						$results.children('li').removeClass(options.selectClass);
						$(this).addClass(options.selectClass);
					})
					.click(function(e) {
						e.preventDefault(); 
						e.stopPropagation();
						selectCurrentResult();
					});
							
			}
			
			function parseTxt(txt, q) {
				
				var items = [];
				var tokens = txt.split(options.delimiter);
				
				// parse returned data for non-empty items
				for (var i = 0; i < tokens.length; i++) {
					var token = $.trim(tokens[i]);
					if (token) {
						token = token.replace(
							new RegExp(q, 'ig'), 
							function(q) { return '<span class="' + options.matchClass + '">' + q + '</span>' }
							);
						items[items.length] = token;
					}
				}
				
				return items;
			}
			
			function parseJson(json, q)
			{
			    var items = [];
			    var array = $(json);
			    if(array.length)
			    {
			        for(var i=0;i<array.length;i++)
			        {
			            var txt = array[i][options.displayText];
			            var val = array[i][options.displayValue];
			            if(txt)
			            {
			                array[i][options.displayText] = txt.replace(
							new RegExp(q, 'ig'), 
							function(q) { return '<span class="' + options.matchClass + '">' + q + '</span>' }
							);
						    items[items.length] = array[i];
			            }
			        }
			    }
			    return items;
			}
			
			function getCurrentResult() {
			
				if (!$results.is(':visible'))
					return false;
			
				var $currentResult = $results.children('li.' + options.selectClass);
				
				if (!$currentResult.length)
					$currentResult = false;
					
				return $currentResult;

			}
			
			function selectCurrentResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult) {
				    if(options.itemType == "text")
				        $input.val($currentResult.text());
				     else
				     {
				        $input.val($currentResult.text());
				        $input.attr("selectedValue",$currentResult.attr("value"));
				     }
					$results.hide();
					
					if (options.onSelect)
						options.onSelect.apply($input[0]);
						
				}
			
			}
			
			function nextResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.next()
							.addClass(options.selectClass);
				else
					$results.children('li:first-child').addClass(options.selectClass);
			
			}
			
			function prevResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.prev()
							.addClass(options.selectClass);
				else
					$results.children('li:last-child').addClass(options.selectClass);
			
			}
	
		}
		
		$.fn.suggest = function(source, options) {
		
			if (!source)
				return;
		
			options = options || {};
			options.source = source;
			options.delay = options.delay || 100;
			options.resultsClass = options.resultsClass || 'ac_results';
			options.selectClass = options.selectClass || 'ac_over';
			options.matchClass = options.matchClass || 'ac_match';
			options.minchars = options.minchars || 2;
			options.delimiter = options.delimiter || '\n';
			options.onSelect = options.onSelect || false;
			options.itemType = options.itemType || "text";//text or json
			options.displayText = options.displayText || '';
			options.displayValue = options.displayValue || '';
			options.maxCacheSize = options.maxCacheSize || 65536;
			options.queryCondition = options.queryCondition || false;
	        options.cached = options.cached || false;
	        
			this.each(function() {
				new $.suggest(this, options);
			});
	
			return this;
			
		};
		
	})(jQuery);
	
